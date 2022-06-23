package com.qts.gopik_loan.Supply_Chain;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.IOUtils;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Fragment.My_Mall_Fragment;
import com.qts.gopik_loan.Model.Dealer_adhar_molldoc_MODEL;
import com.qts.gopik_loan.Model.Dealer_bank_molldoc_MODEL;
import com.qts.gopik_loan.Model.Dealer_pan_molldoc_MODEL;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Image_Upload_SupplyChain extends AppCompatActivity {
    public int x = 0;
    public int y = 0;
    String filePath = "";
    private int GALLERY = 1, CAMERA = 2;
    Integer Bill_Image_saved_status1 = 0;
    Integer ApplicationImage_saved_status = 0;
    Integer MarginPhoto_saved_status = 0;
    Integer Income_saved_status = 0;
    Integer Bank_statment_saved_status = 0;
    Integer upload_adhaar_valid = 0;
    Integer upload_applcation_valid = 0;
    Integer upload_income_valid = 0;
    Integer upload_bank_valid = 0;
    Integer upload_land_valid = 0;
    Integer upload_mrgn_valid = 0;

    Integer upload_uploadelectricitybillimg_valid = 0;
    Integer upload_applicationphptographimg_valid = 0;
    TextView btsend, save1, save2, save3, save4;
    Integer save_success1 = 0;
    Integer save_success2 = 0;
    Integer save_success3 = 0;

    Integer save_success4 = 0;

    ImageView arrow;
    ImageView adhaar_upld_sucss,
            bill_upld_sucss, upld_mrgn_succ, income_success_img, land_upld_sccs, upload_bank_succs, upld_application_succss;
    private static final int FILE_CHOOSER=123;
    ImageView upload_adhaar,upload_adhaar_back,upload_pancard_front_button,upld_pan_back_button,upload_bank_statement,upload_salaryslip;
    ImageView
            dropdown1, dropdown_App_photo, dropdown_margin_photo, dropdown_income_photo, dropdown_bank_photo, dropdown_lanf_photo;
    ImageView dropup1, dropup2, dropup3, dropup4, dropup5, dropup6;
    LinearLayout upload_adhaar_front_layout, application_copy_layout, margin_copy_layout, income_proof_layout, bank_statement_layout, land_docu_layout;
    TextView unsaved_img_error_massage,unsaved_img_error_massage2,unsaved_img_error_massage3,unsaved_img_error_massage4,unsaved_img_error_massage5;
    TextView
            upld_img_hint, upld_appltn_hint, upld_mrgn_hint, upld_income_proof, upld_bank_stmnt_hint, error1;
    TextView uploadelectricitybill,pdf_name;
    ImageView pdf_upload_success;

    ImageView dropdown_pan_front,dropup2_pan_front,dropdown1_adhar_front;
    ImageView dropdown_pan_back,dropup2_pan_back,dropup1_adhar_front;

    LinearLayout pan_front_layout;

    private static final String IMAGE_DIRECTORY = "/supplychaingopikmoneyimg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload_supply_chain);

        //Adhaar
        upload_adhaar = (ImageView) findViewById(R.id.upload_adhaar);
        upload_adhaar_back = (ImageView) findViewById(R.id.upload_adhaar_back);
        //Pan Card
        upload_pancard_front_button = (ImageView) findViewById(R.id.upload_pancard_front_button);
        upld_pan_back_button = (ImageView) findViewById(R.id.upld_pan_back_button);
        //Business Proof
        upload_bank_statement = (ImageView) findViewById(R.id.upload_bank_statement);
        upload_salaryslip = (ImageView) findViewById(R.id.upload_salaryslip);


        uploadelectricitybill = (TextView) findViewById(R.id.uploadelectricitybill);
        pdf_name = (TextView) findViewById(R.id.pdf_name);
        pdf_upload_success = (ImageView) findViewById(R.id.pdf_upload_success);

        //Dropdown

        dropdown1_adhar_front = findViewById(R.id.dropdown1_adhar_front);
        dropup1_adhar_front = findViewById(R.id.dropup1_adhar_front);




        dropup2 = findViewById(R.id.dropup2);

        dropup3 = findViewById(R.id.dropup3);
        dropup4 = findViewById(R.id.dropup4);
        //Save Button
        btsend = (TextView) findViewById(R.id.btsend);
        save1 = (TextView) findViewById(R.id.save1);
        save2 = (TextView) findViewById(R.id.save2);
        save3 = (TextView) findViewById(R.id.save3);
        save4 = (TextView) findViewById(R.id.save4);
        adhaar_upld_sucss = (ImageView) findViewById(R.id.adhaar_upld_sucss);
        upld_mrgn_succ = (ImageView) findViewById(R.id.upld_mrgn_succ);
        income_success_img =
                (ImageView) findViewById(R.id.income_success_img);
        land_upld_sccs = (ImageView) findViewById(R.id.land_upld_sccs);
        upload_bank_succs =
                (ImageView) findViewById(R.id.upload_bank_succs);
        upld_application_succss =
                (ImageView) findViewById(R.id.upld_application_succss);

        upload_adhaar_front_layout = findViewById(R.id.upload_adhaar_front_layout);
        application_copy_layout =
                findViewById(R.id.upload_applctn_copy);
        margin_copy_layout = findViewById(R.id.upload_margin_layout);
        income_proof_layout = findViewById(R.id.upload_income_layout);

        arrow = findViewById(R.id.arrow);



       dropdown_App_photo = findViewById(R.id.dropdown_App_photo);


        dropdown_margin_photo =
                findViewById(R.id.dropdown_margin_photo);
        dropdown_income_photo =
                findViewById(R.id.dropdown_income_photo);
        dropdown_bank_photo = findViewById(R.id.dropdown_bank_photo);
        dropdown_lanf_photo = findViewById(R.id.dropdown_lanf_photo);

        upload_adhaar_front_layout = findViewById(R.id.upload_adhaar_front_layout);
        application_copy_layout =
                findViewById(R.id.upload_applctn_copy);
        margin_copy_layout = findViewById(R.id.upload_margin_layout);

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        income_proof_layout = findViewById(R.id.upload_income_layout);


        upld_img_hint = (TextView) findViewById(R.id.upld_img_hint);
        upld_appltn_hint =
                (TextView) findViewById(R.id.upld_appltn_hint);
        upld_mrgn_hint = (TextView) findViewById(R.id.upld_mrgn_hint);
        upld_income_proof =
                (TextView) findViewById(R.id.upld_income_proof);
        upld_bank_stmnt_hint =
                (TextView) findViewById(R.id.upld_bank_stmnt_hint);
        error1 = (TextView) findViewById(R.id.error1);

        unsaved_img_error_massage = (TextView) findViewById(R.id.unsaved_img_error_massage);
        unsaved_img_error_massage2 = (TextView) findViewById(R.id.unsaved_img_error_massage2);
        unsaved_img_error_massage3= (TextView)findViewById(R.id.unsaved_img_error_massage3);

/*
        dropdown1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropup1.setVisibility(View.VISIBLE);
                upload_adhaar_front_layout.setVisibility(View.VISIBLE);
                dropdown1.setVisibility(View.GONE);
            }
        });
*/


/*
        dropup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdown1.setVisibility(View.VISIBLE);
                dropup1.setVisibility(View.GONE);
                upload_adhaar_front_layout.setVisibility(View.GONE);
            }
        });
*/
/*
        dropdown_App_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropup2.setVisibility(View.VISIBLE);
                dropdown_App_photo.setVisibility(View.GONE);
                application_copy_layout.setVisibility(View.VISIBLE);
            }
        });
*/
/*
        dropup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdown_App_photo.setVisibility(View.VISIBLE);
                dropup2.setVisibility(View.GONE);
                application_copy_layout.setVisibility(View.GONE);
            }
        });
*/

/*
        dropdown_margin_photo.setOnClickListener(new
                                                         View.OnClickListener() {
                                                             @Override
                                                             public void onClick(View v) {
                                                                 dropup3.setVisibility(View.VISIBLE);
                                                                 dropdown_margin_photo.setVisibility(View.GONE);
                                                                 margin_copy_layout.setVisibility(View.VISIBLE);
                                                             }
                                                         });
*/
/*
        dropup3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropup3.setVisibility(View.GONE);
                dropdown_margin_photo.setVisibility(View.VISIBLE);
                margin_copy_layout.setVisibility(View.GONE);
            }
        });
*/

/*
        dropdown_income_photo.setOnClickListener(new
                                                         View.OnClickListener() {
                                                             @Override
                                                             public void onClick(View v) {
                                                                 dropdown_income_photo.setVisibility(View.GONE);
                                                                 dropup4.setVisibility(View.VISIBLE);
                                                                 income_proof_layout.setVisibility(View.VISIBLE);
                                                             }
                                                         });
*/
/*
        dropup4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdown_income_photo.setVisibility(View.VISIBLE);
                dropup4.setVisibility(View.GONE);
                income_proof_layout.setVisibility(View.GONE);
            }
        });
*/

/*
        dropdown_bank_photo.setOnClickListener(new
                                                       View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
                                                               dropdown_bank_photo.setVisibility(View.GONE);
                                                               dropup5.setVisibility(View.VISIBLE);
                                                               bank_statement_layout.setVisibility(View.VISIBLE);
                                                           }
                                                       });
*/
        upload_adhaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
                x = 1;
                y = 1;
            }
        });
        upload_pancard_front_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
                x = 2;
                y = 2;
            }
        });

        upload_bank_statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upld_mrgn_hint.setVisibility(View.GONE);
                try {
                    Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
                    intentPDF.setType("application/pdf");
                    intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(Intent.createChooser(intentPDF, "Select Picture"), FILE_CHOOSER);
                } catch (Exception exe) {
                    exe.printStackTrace();
                }
               /* if (ApplicationImage_saved_status==1){
                    //showonlyCamera();



                    y = 3;
                    Log.e("TAG", "sai5");
                }
                else {
                    unsaved_img_error_massage2.setVisibility(View.VISIBLE);
                }*/
            }
        });
        upload_salaryslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               upld_income_proof.setVisibility(View.GONE);
                try {
                    Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
                    intentPDF.setType("application/pdf");
                    intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(Intent.createChooser(intentPDF, "Select Picture"), FILE_CHOOSER);
                } catch (Exception exe) {
                    exe.printStackTrace();
                }

               /* if (MarginPhoto_saved_status==1){
                   // showonlyGallery();
                    x = 4;
                    Log.e("TAG", "sai4");
                }else{
                    unsaved_img_error_massage3.setVisibility(View.VISIBLE);
                }*/
            }
        });
/*
        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upload_adhaar_valid == 1 ||
                        upload_uploadelectricitybillimg_valid == 1) {
                    upld_img_hint.setVisibility(View.GONE);
                    Dealer_adhar_molldoc();
                    Bill_Image_saved_status1=1;
                    upload_adhaar_front_layout.setVisibility(View.GONE);
                    Toast.makeText(Image_Upload_SupplyChain.this, "Adhaar Upload Successfully", Toast.LENGTH_SHORT).show();
                    adhaar_upld_sucss.setVisibility(View.VISIBLE);
                    unsaved_img_error_massage.setVisibility(View.GONE);
                } else {
                    upld_img_hint.setVisibility(View.VISIBLE);
                }

            }
        });
*/
/*
        save2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("hyfu", "gjhgj" + upload_applcation_valid);
                if (upload_applcation_valid == 2 ||
                        upload_applicationphptographimg_valid == 1) {
                    Log.e("hyfu", "gjhgj");
                    upld_appltn_hint.setVisibility(View.GONE);
                    Dealer_pan_molldoc();
                    Toast.makeText(Image_Upload_SupplyChain.this, "Pan Card Upload Successfully", Toast.LENGTH_SHORT).show();
                    upld_application_succss.setVisibility(View.VISIBLE);
                    application_copy_layout.setVisibility(View.GONE);
                    ApplicationImage_saved_status=1;
                    unsaved_img_error_massage2.setVisibility(View.GONE);
                } else {
                    Log.e("hyfu", "gjhgj");
                    upld_appltn_hint.setVisibility(View.VISIBLE);
                }


            }
        });
*/
/*
        save3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (upload_mrgn_valid == 6) {
                    upld_mrgn_hint.setVisibility(View.GONE);
                    Dealer_bank_molldoc();
                    MarginPhoto_saved_status=1;
                    Toast.makeText(Image_Upload_SupplyChain.this, "Bank Statement Upload Successfully", Toast.LENGTH_SHORT).show();
                    unsaved_img_error_massage3.setVisibility(View.GONE);
                    margin_copy_layout.setVisibility(View.GONE);
                    upld_mrgn_succ.setVisibility(View.VISIBLE);
                } else {
                    upld_mrgn_hint.setVisibility(View.VISIBLE);

                }


            }
        });
*/

/*
        save4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upload_income_valid == 3) {
                    upld_income_proof.setVisibility(View.GONE);
                   // Store_Income_Document_Details();
                    Toast.makeText(Image_Upload_SupplyChain.this, "Salary Slip Upload Successfully", Toast.LENGTH_SHORT).show();
                    unsaved_img_error_massage4.setVisibility(View.GONE);
                    income_proof_layout.setVisibility(View.GONE);
                    income_success_img.setVisibility(View.VISIBLE);
                    Income_saved_status=1;
                } else {
                    upld_income_proof.setVisibility(View.VISIBLE);
                }

            }
        });
*/
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Image_Upload_SupplyChain.this, My_Mall_Fragment.class);
            }
        });
    }

    private void Dealer_pan_molldoc() {

        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("panimage", idFile.getName(), mFile1);


        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_pan_molldoc_MODEL> call = restApis.Dealer_pan_molldoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<Dealer_pan_molldoc_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_pan_molldoc_MODEL> call, Response<Dealer_pan_molldoc_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "success_pan");

                }



            }

            @Override
            public void onFailure(Call<Dealer_pan_molldoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }


    /*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            try {
                if (requestCode == FILE_CHOOSER && resultCode == RESULT_OK && data.getData() != null) {

                    Uri fileuri = data.getData();


                    Log.e("fileuriii","flUri"+fileuri);

                    // filePath = getStringPdf(fileuri);
                    Log.e("filePath","filePath2"+filePath);

                    String file_Name = fileuri.toString();
                    Log.e("file_Name1","file_Name2"+file_Name);
                    // pdf_name.setText(file_Name);


                    Toast.makeText(this, "Selected pdf file "+file_Name, Toast.LENGTH_SHORT).show();




                    String filePathh1 = getFilePathFromURI(Image_Upload_SupplyChain.this, fileuri,unsaved_img_error_massage2);

                    Log.e("filePathh1","filePathh112"+filePathh1);



                    File file = new File(filePathh1);


                    unsaved_img_error_massage2.setText(Html.fromHtml(file_Name));


                   // upld_success.setVisibility(View.VISIBLE);
                    //  file_size1 = Integer.parseInt(String.valueOf(file.length() / 1024));


                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    */
    public String getFilePathFromURI(Context context, Uri contentUri, TextView pdf_name) {
//copy file and send new file path
        String fileName = getFileName(Image_Upload_SupplyChain.this, contentUri);
        Log.e("filee","files"+fileName);
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(context.getExternalFilesDir("") + File.separator + fileName);
            Log.e("filee1x","files1xz"+copyFile);
            copy(context, contentUri, copyFile);
            Log.e("filee1xv","filesss"+fileName);
            return copyFile.getAbsolutePath();

        }
        return null;
    }
    public static String getFileName(Image_Upload_SupplyChain  image_upload_supplyChain, Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        Log.e("logggFile","path..."+path);

        int cut = path.lastIndexOf('/');
        Log.e("filenameXYZPath","XYZP"+cut);
        if (cut != -1) {
            fileName = path.substring(cut + 1);

            Log.e("filenameXYZ","XYZ"+fileName);

        }
        return fileName;

    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            IOUtils.copyStream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showonlyCamera() {

        AlertDialog.Builder pictureDialog = new
                AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int
                            which) {
                        switch (which) {
                            case 0:
                                takePhotoFromCamera();
                                break;

                        }
                    }
                });
        pictureDialog.show();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new
                AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int
                            which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();

    }

    private void takePhotoFromCamera() {
        Intent intent = new
                Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void choosePhotoFromGallary() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,

                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent
            data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap =
                            MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                                    contentURI);
                    if (x == 1) {
                        upload_adhaar.setImageBitmap(bitmap);
                        upload_adhaar_valid = 1;
                        upld_img_hint.setVisibility(View.GONE);
                    } else if (x == 2) {
                        upload_pancard_front_button.setImageBitmap(bitmap);
                       upload_applcation_valid = 2;
                        upld_appltn_hint.setVisibility(View.GONE);
                    } else if (x == 4) {
                       // income_proof_image.setImageBitmap(bitmap);
                       // upload_income_valid = 3;

                    } else if (x == 3) {
                       // uploadbankpassbookimg.setImageBitmap(bitmap);
                       // upload_bank_valid = 4;
                    } else if (x == 5) {
                       // uploadlandimg.setImageBitmap(bitmap);
                       // upload_land_valid = 5;

                    }
                   saveImage(bitmap);

                } catch (IOException e) {

                    e.printStackTrace();

                    // Toast.makeText(getActivity(), "Failed!",Toast.LENGTH_SHORT).show();
                }
            }


        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");


            if (y == 1) {
               // upload_uploadelectricitybillimg_valid = 1;
                upload_adhaar.setImageBitmap(thumbnail);
                upld_img_hint.setVisibility(View.GONE);
            } else if (y == 2) {
                upload_applicationphptographimg_valid = 1;
                upload_pancard_front_button.setImageBitmap(thumbnail);

            } else if (y == 3) {
                //uploadmargincopyimg.setImageBitmap(thumbnail);

               // upload_mrgn_valid = 6;
            }

          saveImage(thumbnail);
            //  Toast.makeText(getActivity(), "Image Saved!",Toast.LENGTH_SHORT).show();
        }else if (requestCode == FILE_CHOOSER && resultCode == RESULT_OK && data.getData() != null){
            Uri fileuri = data.getData();


            Log.e("fileuriii","flUri"+fileuri);
            filePath = getStringPdf(fileuri);
            Log.e("filePath","filePath2"+filePath);

            String file_Name = fileuri.toString();

            Log.e("file_Name1","file_Name2"+file_Name);
            // pdf_name.setText(file_Name);


            Toast.makeText(this, "Selected pdf file "+file_Name, Toast.LENGTH_SHORT).show();




            String filePathh1 = getFilePathFromURI(Image_Upload_SupplyChain.this, fileuri,pdf_name);

            Log.e("filePathh1","filePathh112"+filePathh1);



            File file = new File(filePathh1);


            pdf_name.setText(Html.fromHtml(file_Name));


            SharedPref.saveStringInSharedPref(AppConstants.SUPPLY_CHAIN_PDF_FILE,pdf_name.getText().toString(),getApplicationContext());


             pdf_upload_success.setVisibility(View.VISIBLE);
            upload_mrgn_valid = 6;
            //  file_size1 = Integer.parseInt(String.valueOf(file.length() / 1024));



        }

    }
    public String getStringPdf(Uri filepath) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


        try {
            inputStream = getContentResolver().openInputStream(filepath);

            byte[] buffer = new byte[1024];
            byteArrayOutputStream = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        byte[] pdfByteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(pdfByteArray, Base64.DEFAULT);
    }
    public String saveImage(Bitmap myBitmap) {
        Log.e("TAG", "File Saved1");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Log.e("TAG", "File Saved2");
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        Log.e("TAG", "File Saved3");
        File wallpaperDirectory = new File(

                Environment.getExternalStorageDirectory().getAbsolutePath() +
                        IMAGE_DIRECTORY);
        Log.e("Full Path", Environment.getExternalStorageDirectory() +
                IMAGE_DIRECTORY);
        Log.e("TAG", "File Saved4");
        // have the object build the directory structure, if needed.
        Log.e("Boolean Value", Boolean.toString(wallpaperDirectory.exists()));
        if (!wallpaperDirectory.exists()) {
            Log.e("TAG", "File Saved5");
            wallpaperDirectory.mkdir();
            Log.e("Yest", Boolean.toString(wallpaperDirectory.mkdir()));

        }
        Log.e("TAG", "File Saved6");
        try {
            Log.e("TAG", "File Saved7");
            File f = new
                    File(getExternalFilesDir(
                    Environment.DIRECTORY_DOWNLOADS), "gopikmoneykyc1");
            Log.e("TAG", "File Saved8");
            f.createNewFile();
            Log.e("TAG", "File Saved9");
            FileOutputStream fo = new FileOutputStream(f);
            Log.e("TAG", "File Saved10");

            fo.write(bytes.toByteArray());
            Log.e("TAG", "File Saved11");
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            Log.e("TAG", "File Saved12");
            fo.close();
            Log.e("TAG", "File Saved::--->" + f.getAbsolutePath());

            SharedPref.saveStringInSharedPref(AppConstants.ML_LOAN_IMAGE, f.getAbsolutePath(), getApplicationContext());
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void Dealer_adhar_molldoc() {

        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);


        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("adharimage", idFile.getName(), mFile1);


        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_adhar_molldoc_MODEL> call = restApis.Dealer_adhar_molldoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<Dealer_adhar_molldoc_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_adhar_molldoc_MODEL> call, Response<Dealer_adhar_molldoc_MODEL> response) {
                if (response.body() != null) {
                 upload_adhaar.setImageResource(response.body().getPayload().getAdhar_img());
                    Log.e("testingggg", "success");

                }



            }

            @Override
            public void onFailure(Call<Dealer_adhar_molldoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }


    private void Dealer_bank_molldoc() {

        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);


        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.SUPPLY_CHAIN_PDF_FILE, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("bankimage", idFile.getName(), mFile1);


        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_bank_molldoc_MODEL> call = restApis.Dealer_bank_molldoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<Dealer_bank_molldoc_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_bank_molldoc_MODEL> call, Response<Dealer_bank_molldoc_MODEL> response) {
                if (response.body() != null) {


                    Log.e("testingggg", "success");

                }



            }

            @Override
            public void onFailure(Call<Dealer_bank_molldoc_MODEL> call, Throwable t) {
                Log.e("testingggg", "success"+t);

                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

}