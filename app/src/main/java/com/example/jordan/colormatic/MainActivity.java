package com.example.jordan.colormatic;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telecom.VideoProfile;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 *
 *  @author ColormaticTeam
 *
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AndroidCameraApi";
    private TextureView textureView;
    private TextView myTextView;

    private boolean p1 = false; // preset1
    private boolean p2 = false; // preset2
    private boolean p3 = false; // preset3

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSessions;
    private CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private File file = null;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;

    private List<Preset> presetList;

    public static final String APP_PREFS = "APPLICATION_PREFERENCES";
    public static final String TEST_TEXT = "TEXT";

    private Bitmap bitmap;

    private Map<String, Integer> mColors = new HashMap<String, Integer>();

    /**
     * Creates camera object and respective variables
     *
     * @author ColormaticTeam
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textureView = findViewById(R.id.texture);
        assert textureView != null;
        textureView.setSurfaceTextureListener(textureListener);
        ImageButton crosshairButton = findViewById(R.id.crosshair_btn);
        final ImageButton menu = findViewById(R.id.btn_menu);
        Button takePictureButton = findViewById(R.id.btn_takepicture);

        textureView.setDrawingCacheEnabled(true);
        textureView.buildDrawingCache(true);

        assert menu != null;
        assert takePictureButton != null;
        assert crosshairButton != null;

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, menu);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (Objects.equals("Settings", menuItem.getTitle())) {
                            Toast.makeText(MainActivity.this, "New Activity", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(MainActivity.this, advanceSetting.class));
                            startActivity(new Intent(MainActivity.this, CreatePreset.class)); // just a shortcut until we can have the 2nd and 3rd activity communicate
                        }
                        else if (Objects.equals("Deuteranomaly", menuItem.getTitle())) {
                            Toast.makeText(MainActivity.this, "Locate your visual accessibility settings", Toast.LENGTH_LONG).show();
                            Toast.makeText(MainActivity.this, "Turn on your color adjustment for your specific colorblindness", Toast.LENGTH_LONG).show();
                            startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 0);
                            if (p1 == true) {
                                p1 = false;
                            }
                            else {
                                p1 = true;
                                p2 = false;
                                p3 = false;
                            }
                        }
                        else if (Objects.equals("Protanomaly", menuItem.getTitle())) {
                            if (p2 == true) {
                                p2 = false;
                            }
                            else {
                                p1 = false;
                                p2 = true;
                                p3 = false;
                            }
                        }
                        else if (Objects.equals("Tritanomaly", menuItem.getTitle())) {
                            if (p3 == true) {
                                p3 = false;
                            }
                            else {
                                p1 = false;
                                p2 = false;
                                p3 = true;
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Something went wrong??", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    public void crosshairButton(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        Toast.makeText(this, "Crosshair Button Pressed", Toast.LENGTH_SHORT).show();
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            myTextView = findViewById(R.id.textView);
            //open your camera here
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            // Transform the image captured size according to the surface width and height
        }
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            bitmap = textureView.getBitmap();

            int pixel = bitmap.getPixel((bitmap.getWidth() / 2), (bitmap.getHeight() / 2));
            int r = Color.red(pixel);
            int g = Color.green(pixel);
            int b = Color.blue(pixel);
            String colorName = "";

            mColors.put("Maroon", Color.rgb(128, 0, 0));
            mColors.put("Red", Color.rgb(255, 0, 0));
            mColors.put("Orange-Red", Color.rgb(255, 69,0));
            mColors.put("Dark Orange", Color.rgb(255, 140, 0));
            mColors.put("Orange", Color.rgb(255, 165, 0));
            mColors.put("Gold", Color.rgb(255, 215, 0));
            mColors.put("Yellow", Color.rgb(255, 255, 0));
            mColors.put("Khaki", Color.rgb(240, 230, 140));
            mColors.put("Tan", Color.rgb(210, 180, 140));
            mColors.put("Lime Green", Color.rgb(0, 255, 0));
            mColors.put("Green", Color.rgb(0, 128, 0));
            mColors.put("Sea Green", Color.rgb(46, 139, 87));
            mColors.put("Teal", Color.rgb(0,128,128));
            mColors.put("Turquoise", Color.rgb(64,224,208));
            mColors.put("Light Blue", Color.rgb(173, 216, 230));
            mColors.put("Deep Sky Blue", Color.rgb(0, 191, 222));
            mColors.put("Blue", Color.rgb(0, 0, 255));
            mColors.put("Dark Blue", Color.rgb(0, 0, 139));
            mColors.put("Indigo", Color.rgb(75, 0, 130));
            mColors.put("Dark Magenta", Color.rgb(139, 0, 139)); // r 155 b 255
            mColors.put("Violet", Color.rgb(148, 0, 211));
            mColors.put("Light Pink", Color.rgb(255,182,193));
            mColors.put("Pink", Color.rgb(255, 0, 255));
            mColors.put("Hot Pink", Color.rgb(255,20,147));
            mColors.put("Fuschia", Color.rgb(199,21,133));
            mColors.put("White", Color.rgb(255, 255, 255));
            mColors.put("Black", Color.rgb(0, 0, 0));
            mColors.put("Silver", Color.rgb(192,192,192));
            mColors.put("Light Gray", Color.rgb(211, 211,211));
            mColors.put("Gray", Color.rgb(128, 128, 128));
            mColors.put("Brown", Color.rgb(139, 69, 19));
            mColors.put("Russet Brown", Color.rgb(165,42,42));
            mColors.put("Light Brown", Color.rgb(210, 105,30));
            mColors.put("Beige", Color.rgb(245,245,220));

            colorName = getBestMatchingColorName(pixel);

            myTextView.setBackgroundColor(Color.rgb(0, 0, 0)); // set background to black
            myTextView.setText(colorName);
//            myTextView.setText("R(" + r + ")\n" + "G(" + g + ")\n" + "B(" + b + ")");
        }
    };

    private String getBestMatchingColorName(int pixelColor) {
        // largest difference is 255 for every colour component
        int currentDifference = 3 * 255;
        // name of the best matching colour
        String closestColorName = null;
        // get int values for all three colour components of the pixel
        int pixelColorR = Color.red(pixelColor);
        int pixelColorG = Color.green(pixelColor);
        int pixelColorB = Color.blue(pixelColor);

        Iterator<String> colorNameIterator = mColors.keySet().iterator();
        // continue iterating if the map contains a next colour and the difference is greater than zero.
        // a difference of zero means we've found an exact match, so there's no point in iterating further.
        while (colorNameIterator.hasNext() && currentDifference > 0) {
            // this colour's name
            String currentColorName = colorNameIterator.next();
            // this colour's int value
            int color = mColors.get(currentColorName);
            // get int values for all three colour components of this colour
            int colorR = Color.red(color);
            int colorG = Color.green(color);
            int colorB = Color.blue(color);
            // calculate sum of absolute differences that indicates how good this match is
            int difference = Math.abs(pixelColorR - colorR) + Math.abs(pixelColorG - colorG) + Math.abs(pixelColorB - colorB);
            // a smaller difference means a better match, so keep track of it
            if (currentDifference > difference) {
                currentDifference = difference;
                closestColorName = currentColorName;
            }
        }
        return closestColorName;
    };

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            //This is called when the camera is open
            Log.e(TAG, "onOpened");
            cameraDevice = camera;
            createCameraPreview();
        }
        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            cameraDevice.close();
        }
        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };
    final CameraCaptureSession.CaptureCallback captureCallbackListener = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
            Toast.makeText(MainActivity.this, "Saved:" + file, Toast.LENGTH_SHORT).show();
            createCameraPreview();
        }
    };
    protected void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }
    protected void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected void takePicture() {
        if(null == cameraDevice) {
            Log.e(TAG, "cameraDevice is null");
            return;
        }
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            assert manager != null;
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
            Size[] jpegSizes = null;
            if (characteristics != null) {
                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);
            }
            int width = 640;
            int height = 480;
            if (jpegSizes != null && 0 < jpegSizes.length) {
                width = jpegSizes[0].getWidth();
                height = jpegSizes[0].getHeight();
            }
            ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
            List<Surface> outputSurfaces = new ArrayList<>(2);
            outputSurfaces.add(reader.getSurface());
            outputSurfaces.add(new Surface(textureView.getSurfaceTexture()));
            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(reader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
            // Orientation
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
            final File file = new File(Environment.getExternalStorageDirectory()+"/colormaticimage" + "/ColormaticImagepic.jpg");


            ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    Image image = null;
                    try {
                        image = reader.acquireLatestImage();
                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        byte[] bytes = new byte[buffer.capacity()];
                        buffer.get(bytes);
                        save(bytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (image != null) {
                            image.close();
                        }
                    }
                }
                private void save(byte[] bytes) throws IOException {
                    OutputStream output = null;
                    try {
                        output = new FileOutputStream(file);
                        output.write(bytes);
                    } finally {
                        if (null != output) {
                            output.close();
                        }
                    }
                }
            };
            reader.setOnImageAvailableListener(readerListener, mBackgroundHandler);
            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    Toast.makeText(MainActivity.this, "Saved:" + file, Toast.LENGTH_SHORT).show();
                    createCameraPreview();
                }
            };
            cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    try {
                        session.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                }
            }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    protected void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Collections.singletonList(surface), new CameraCaptureSession.StateCallback(){
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    //The camera is already closed
                    if (null == cameraDevice) {
                        return;
                    }
                    // When the session is ready, we start displaying the preview.
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }
                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(MainActivity.this, "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        Log.e(TAG, "is camera open");
        try {
            assert manager != null;
            String cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            // Add permission for camera and let user grant the permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "openCamera X");
    }
    protected void updatePreview() {
        if(null == cameraDevice) {
            Log.e(TAG, "updatePreview error, return");
        }
        if (p1 == true) {
//            captureRequestBuilder.set(CaptureRequest.CONTROL_AWB_MODE, CaptureRequest.CONTROL_AWB_MODE_OFF);
//            captureRequestBuilder.set(CaptureRequest.COLOR_CORRECTION_MODE, CaptureRequest.COLOR_CORRECTION_MODE_TRANSFORM_MATRIX);
//            // captureRequestBuilder.set(CaptureRequest.COLOR_CORRECTION_TRANSFORM, CaptureRequest.COLOR_CORRECTION_GAINS)
//            colorCorrection.transform = [ 0 1 2 3 4 5 6 7 8 9]
        }
        else if (p2 == true) {

        }
        else if (p3 == true) {

        }
        else {
            captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        }
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void closeCamera() {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // close the app
                Toast.makeText(MainActivity.this, "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        startBackgroundThread();
        if (textureView.isAvailable()) {
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }
    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    /* color editing functions */
    // takes a bitmap object as a parameter and changes it to the specified color in the preset class, and returns the new color.
    private Bitmap changeHue(Bitmap color) {return color;}

    private void setHexColor() {
        String hexCode = "";
    }
}
