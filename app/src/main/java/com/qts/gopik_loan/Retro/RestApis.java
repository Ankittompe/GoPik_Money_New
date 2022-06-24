package com.qts.gopik_loan.Retro;

import android.widget.TextView;

import com.qts.gopik_loan.Model.*;
import com.qts.gopik_loan.Model.AADHAR_CONSENT_MODEL;
import com.qts.gopik_loan.Model.ACCOUNT_NO_MODEL;
import com.qts.gopik_loan.Model.Aadhaarverification_MODEL;
import com.qts.gopik_loan.Model.Aadhar_OTP_Verify_MODEL;
import com.qts.gopik_loan.Model.Add_scratchcard_MODEL;
import com.qts.gopik_loan.Model.Apiget_panid_details_MODEL;
import com.qts.gopik_loan.Model.Bankacc_verification_MODEL;
import com.qts.gopik_loan.Model.Banner_list_MODEL;
import com.qts.gopik_loan.Model.Broker_bank_details_update_MODEL;
import com.qts.gopik_loan.Model.Broker_profile_details_MODEL;
import com.qts.gopik_loan.Model.Broker_profile_update_MODEL;
import com.qts.gopik_loan.Model.Calculation_data_MODEL;
import com.qts.gopik_loan.Model.Category_brand_wise_MODEL;
import com.qts.gopik_loan.Model.DL_MODEL;
import com.qts.gopik_loan.Model.Dealer_QR_MODEL;
import com.qts.gopik_loan.Model.Dealer_Subuser_action_MODEL;
import com.qts.gopik_loan.Model.Dealer_Subuser_edit_MODEL;
import com.qts.gopik_loan.Model.Dealer_Subuser_fetch_MODEL;
import com.qts.gopik_loan.Model.Dealer_Subuser_insert_MODEL;
import com.qts.gopik_loan.Model.Dealer_bank_update_MODEL;
import com.qts.gopik_loan.Model.Dealer_logout_MODEL;
import com.qts.gopik_loan.Model.Draftview_MODEL;
import com.qts.gopik_loan.Model.Fetch_application_list_MODEL;
import com.qts.gopik_loan.Model.Fetch_current_appliation_list_DEALER_MODEL;
import com.qts.gopik_loan.Model.Fetch_current_loanappliation_list_MODEL;
import com.qts.gopik_loan.Model.Fetch_customer_data_MODEL;
import com.qts.gopik_loan.Model.Fetch_loan_application_list_MODEL;
import com.qts.gopik_loan.Model.GetCatListMODEL;
import com.qts.gopik_loan.Model.GetCatproductModel;
import com.qts.gopik_loan.Model.Get_ML_SubCat_List_MODEL;
import com.qts.gopik_loan.Model.Get_loan_status_MODEL;
import com.qts.gopik_loan.Model.Get_status_MODEL;
import com.qts.gopik_loan.Model.Get_voterid_details_MODEL;
import com.qts.gopik_loan.Model.Get_wallet_details_MODEL;
import com.qts.gopik_loan.Model.Get_wallet_txn_MODEL;
import com.qts.gopik_loan.Model.Getcontestresult_MODEL;
import com.qts.gopik_loan.Model.Getusercontest_MODEL;
import com.qts.gopik_loan.Model.GoatAadharvalidation1_MODEL;
import com.qts.gopik_loan.Model.GoatAadharvalidation_MODEL;
import com.qts.gopik_loan.Model.Loan_calculation_Model;
import com.qts.gopik_loan.Model.LogInOtpVerifyMODEL;
import com.qts.gopik_loan.Model.Login_actPOJO;
import com.qts.gopik_loan.Model.Login_act_MODEL;
import com.qts.gopik_loan.Model.Login_otp_verify_for_ML_MODEL;
import com.qts.gopik_loan.Model.LoginsendOtpMODEL;
import com.qts.gopik_loan.Model.MLAddproof_upload_MODEL;
import com.qts.gopik_loan.Model.MLBnkproof_upload_MODEL;
import com.qts.gopik_loan.Model.MLDoc1_upload_MODEL;
import com.qts.gopik_loan.Model.MLDoc3_upload_MODEL;
import com.qts.gopik_loan.Model.MLEmpproof_upload_MODEL;
import com.qts.gopik_loan.Model.MLIDproofBackUpload_MODEL;
import com.qts.gopik_loan.Model.MLIDproofFrontUpload_MODEL;
import com.qts.gopik_loan.Model.MLIDproof_upload_MODEL;
import com.qts.gopik_loan.Model.Notification_MODEL;
import com.qts.gopik_loan.Model.OtpVerificationMODEL;
import com.qts.gopik_loan.Model.PAN_MODEL;
import com.qts.gopik_loan.Model.PASSPORT_MODEL;
import com.qts.gopik_loan.Model.PINCODE_MODEL;
import com.qts.gopik_loan.Model.Pincode_list_MODEL;
import com.qts.gopik_loan.Model.Product_details_Model;
import com.qts.gopik_loan.Model.ProfileDetailsMODEL;
import com.qts.gopik_loan.Model.ProfileDetails_DEALER_MODEL;
import com.qts.gopik_loan.Model.Profile_Update_DEALER_MODEL;
import com.qts.gopik_loan.Model.Profile_data_MODEL;
import com.qts.gopik_loan.Model.QR_ScannedList_MODEL;
import com.qts.gopik_loan.Model.RazorpayOrderResponse;
import com.qts.gopik_loan.Model.RegisterMODEL;
import com.qts.gopik_loan.Model.Resend_login_otp_MODEL;
import com.qts.gopik_loan.Model.Resend_otp_to_user_MODEL;
import com.qts.gopik_loan.Model.Resend_verify_mobile_number_MODEL;
import com.qts.gopik_loan.Model.Send_MLData_to_ICICI_MODEL;
import com.qts.gopik_loan.Model.Send_login_otp_for_ML_MODEL;
import com.qts.gopik_loan.Model.Send_otp_for_loan_MODEL;
import com.qts.gopik_loan.Model.Send_otp_to_user_DEALER_MODEL;
import com.qts.gopik_loan.Model.Send_otp_to_user_MODEL;
import com.qts.gopik_loan.Model.Store_App_Document_Details_MODEL;
import com.qts.gopik_loan.Model.Store_Bank_Document_Details_MODEL;
import com.qts.gopik_loan.Model.Store_Income_Document_Details_MODEL;
import com.qts.gopik_loan.Model.Store_Land_Document_Details_MODEL;
import com.qts.gopik_loan.Model.Store_Margin_Document_Details_MODEL;
import com.qts.gopik_loan.Model.Store_Other_Document_Details_MODEL;
import com.qts.gopik_loan.Model.Store_Utility_Document_Details_MODEL;
import com.qts.gopik_loan.Model.Store_data_for_MLloan_MODEL;
import com.qts.gopik_loan.Model.Store_panid_details_MODEL;
import com.qts.gopik_loan.Model.Store_voterid_back_details_MODEL;
import com.qts.gopik_loan.Model.Submit_customer_application_MODEL;
import com.qts.gopik_loan.Model.Submit_loan_application_MODEL;
import com.qts.gopik_loan.Model.Update_scratchcard_MODEL;
import com.qts.gopik_loan.Model.Update_wallet_credit_MODEL;
import com.qts.gopik_loan.Model.VOTER_MODEL;
import com.qts.gopik_loan.Model.Verify_mobile_number_MODEL;
import com.qts.gopik_loan.Model.Verify_mobile_number_forloan_MODEL;
import com.qts.gopik_loan.Model.View_scratchcard_MODEL;
import com.qts.gopik_loan.Model.Wallet_balance_MODEL;
import com.qts.gopik_loan.Model.Wallethistory_MODEL;
import com.qts.gopik_loan.Model.WhatsAppStatusList_MODEL;
import com.qts.gopik_loan.Model.bkr_declrtn_MODEL;
import com.qts.gopik_loan.Model.store_voterid_details_MODEL;
import com.qts.gopik_loan.Pojo.AADHAR_CONSENT_POJO;
import com.qts.gopik_loan.Pojo.Aadhaarverification_POJO;
import com.qts.gopik_loan.Pojo.Add_scratchcard_POJO;
import com.qts.gopik_loan.Pojo.Apiget_panid_details_POJO;
import com.qts.gopik_loan.Pojo.Bankacc_verification_POJO;
import com.qts.gopik_loan.Pojo.Banner_POJO;
import com.qts.gopik_loan.Pojo.Broker_bank_details_update_POJO;
import com.qts.gopik_loan.Pojo.Broker_profile_details_POJO;
import com.qts.gopik_loan.Pojo.Broker_profile_update_POJO;
import com.qts.gopik_loan.Pojo.Calculation_data_POJO;
import com.qts.gopik_loan.Pojo.Category_brand_wise_POJO;
import com.qts.gopik_loan.Pojo.DL_POJO;
import com.qts.gopik_loan.Pojo.Dealer_CODE_POJO;
import com.qts.gopik_loan.Pojo.Dealer_Subuser_action_POJO;
import com.qts.gopik_loan.Pojo.Dealer_Subuser_edit_POJO;
import com.qts.gopik_loan.Pojo.Dealer_Subuser_fetch_POJO;
import com.qts.gopik_loan.Pojo.Dealer_Subuser_insert_POJO;
import com.qts.gopik_loan.Pojo.Dealer_bank_update_POJO;
import com.qts.gopik_loan.Pojo.Dealer_logoutPOJO;
import com.qts.gopik_loan.Pojo.Draftview_POJO;
import com.qts.gopik_loan.Pojo.Fetch_application_list_POJO;
import com.qts.gopik_loan.Pojo.Fetch_current_appliation_list_DEALER_POJO;
import com.qts.gopik_loan.Pojo.Fetch_current_loanappliation_list_POJO;
import com.qts.gopik_loan.Pojo.Fetch_customer_data_POJO;
import com.qts.gopik_loan.Pojo.Fetch_loan_application_list_POJO;
import com.qts.gopik_loan.Pojo.GetCatListPOJO;
import com.qts.gopik_loan.Pojo.GetCatproductPojo;
import com.qts.gopik_loan.Pojo.Get_ML_SubCat_List_POJO;
import com.qts.gopik_loan.Pojo.Get_loan_status_POJO;
import com.qts.gopik_loan.Pojo.Get_status_POJO;
import com.qts.gopik_loan.Pojo.Get_voterid_details_POJO;
import com.qts.gopik_loan.Pojo.Get_wallet_details_POJO;
import com.qts.gopik_loan.Pojo.Get_wallet_txn_POJO;
import com.qts.gopik_loan.Pojo.Getusercontest_POJO;
import com.qts.gopik_loan.Pojo.GoatAadharvalidation1_POJO;
import com.qts.gopik_loan.Pojo.GoatAadharvalidation_POJO;
import com.qts.gopik_loan.Pojo.GoatAdharvalidationResponseDTO;
import com.qts.gopik_loan.Pojo.Loan_calculation_POJO;
import com.qts.gopik_loan.Pojo.LogInOtpVerifyPOJO;
import com.qts.gopik_loan.Pojo.Login_otp_verify_for_ML_POJO;
import com.qts.gopik_loan.Pojo.LoginsendOtpPOJO;
import com.qts.gopik_loan.Pojo.MLDoc1_upload_POJO;
import com.qts.gopik_loan.Pojo.MLDoc3_upload_POJO;
import com.qts.gopik_loan.Pojo.MLIDproof_upload_POJO;
import com.qts.gopik_loan.Pojo.Notification_POJO;
import com.qts.gopik_loan.Pojo.OtpVerificationPOJO;
import com.qts.gopik_loan.Pojo.PAN_POJO;
import com.qts.gopik_loan.Pojo.PASSPORT_POJO;
import com.qts.gopik_loan.Pojo.Pincode_list_POJO;
import com.qts.gopik_loan.Pojo.Po_add_POJO;
import com.qts.gopik_loan.Pojo.Product_details_POJO;
import com.qts.gopik_loan.Pojo.ProfileDetailsPOJO;
import com.qts.gopik_loan.Pojo.ProfileDetails_DEALER_POJO;
import com.qts.gopik_loan.Pojo.Profile_Update_DEALER_POJO;
import com.qts.gopik_loan.Pojo.Profile_data_POJO;
import com.qts.gopik_loan.Pojo.QR_CODE_POJO;
import com.qts.gopik_loan.Pojo.RazorpayOrderPojo;
import com.qts.gopik_loan.Pojo.RegistrationPOJO;
import com.qts.gopik_loan.Pojo.Resend_login_otp_POJO;
import com.qts.gopik_loan.Pojo.Resend_otp_to_user_POJO;
import com.qts.gopik_loan.Pojo.Resend_verify_mobile_number_POJO;
import com.qts.gopik_loan.Pojo.Send_MLData_to_ICICI_POJO;
import com.qts.gopik_loan.Pojo.Send_login_otp_for_ML_POJO;
import com.qts.gopik_loan.Pojo.Send_otp_for_loan_POJO;
import com.qts.gopik_loan.Pojo.Send_otp_to_user_POJO;
import com.qts.gopik_loan.Pojo.Store_data_for_MLloan_POJO;
import com.qts.gopik_loan.Pojo.Store_panid_details_POJO;
import com.qts.gopik_loan.Pojo.Submit_customer_application_POJO;
import com.qts.gopik_loan.Pojo.Submit_loan_application_POJO;
import com.qts.gopik_loan.Pojo.Update_scratchcard_POJO;
import com.qts.gopik_loan.Pojo.Update_wallet_credit_POJO;
import com.qts.gopik_loan.Pojo.VOTER_POJO;
import com.qts.gopik_loan.Pojo.Verify_mobile_number_POJO;
import com.qts.gopik_loan.Pojo.Verify_mobile_number_forloan_POJO;
import com.qts.gopik_loan.Pojo.View_scratchcard_POJO;
import com.qts.gopik_loan.Pojo.Wallet_balance_POJO;
import com.qts.gopik_loan.Pojo.Wallethistory_POJO;
import com.qts.gopik_loan.Pojo.all_POs_POJO;
import com.qts.gopik_loan.Pojo.bkr_declrtn_POJO;
import com.qts.gopik_loan.Pojo.top_five_POs_POJO;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RestApis {
    @POST("sendotp")
    Call<RegisterMODEL> sendotp_register(@Body RegistrationPOJO data);

    @POST("otpverify")
    Call<OtpVerificationMODEL> otpverify_register(@Body OtpVerificationPOJO data);

    @POST("send_login_otp")
    Call<LoginsendOtpMODEL> send_login_otp(@Body LoginsendOtpPOJO data);


    @POST("login_otp_verify")
    Call<LogInOtpVerifyMODEL> login_otp_verify(@Body LogInOtpVerifyPOJO data);

    @POST("get_status")
    Call<Get_status_MODEL> get_status(@Body Get_status_POJO data);

    @POST("verify_mobile_number_forloan")
    Call<Verify_mobile_number_MODEL> verify_mobile_number_forloan(@Body Verify_mobile_number_POJO data);

    @POST("profile_details")
    Call<ProfileDetails_DEALER_MODEL> profile_details(@Body ProfileDetails_DEALER_POJO data);


    @POST("send_otp_for_MLloan")
    Call<Send_otp_to_user_MODEL> send_otp_for_MLloan(@Body Send_otp_to_user_POJO data);

    @POST("send_otp_for_loan")
    Call<Send_otp_for_loan_MODEL> send_otp_for_loan(@Body Send_otp_for_loan_POJO data);

    @POST("verify_mobile_number_forloan")
    Call<Verify_mobile_number_forloan_MODEL> verify_mobile_number_forloan(@Body Verify_mobile_number_forloan_POJO data);

    @POST("get_loan_status")
    Call<Get_loan_status_MODEL> get_loan_status(@Body Get_loan_status_POJO data);

    @POST(" get_panid_details")
    Call<Apiget_panid_details_MODEL> apiget_panid_details(@Body Apiget_panid_details_POJO data);

    @POST(" submit_loan_application")
    Call<Submit_loan_application_MODEL> submit_loan_application(@Body Submit_loan_application_POJO data);

    @POST(" fetch_current_loanappliation_list")
    Call<Fetch_current_loanappliation_list_MODEL> fetch_current_loanappliation_list(@Body Fetch_current_loanappliation_list_POJO data);

    @POST(" fetch_current_appliation_list")
    Call<Fetch_current_appliation_list_DEALER_MODEL> fetch_current_appliation_list(@Body Fetch_current_appliation_list_DEALER_POJO data);

    @POST(" fetch_loan_application_list")
    Call<Fetch_loan_application_list_MODEL> fetch_loan_application_list(@Body Fetch_loan_application_list_POJO data);

    @POST("GetCatList")
    Call<GetCatListMODEL> getCatList(@Body GetCatListPOJO data);

    @GET("pincode/{pincode}")
    Call<PINCODE_MODEL> pincode(@Path("pincode") String pincode);

    @POST(" store_panid_details")
    Call<Store_panid_details_MODEL> store_panid_details(@Body Store_panid_details_POJO data);

    @GET("{accountno}")
    Call<ACCOUNT_NO_MODEL> account(@Path("accountno") String accountno);

    @POST(" send_login_otp_for_ML")
    Call<Send_login_otp_for_ML_MODEL> send_login_otp_for_ML(@Body Send_login_otp_for_ML_POJO data);


    @POST(" login_otp_verify_for_ML")
    Call<Login_otp_verify_for_ML_MODEL> login_otp_verify_for_ML(@Body Login_otp_verify_for_ML_POJO data);

    @POST(" bkr_declrtn")
    Call<bkr_declrtn_MODEL> bkr_declrtn(@Body bkr_declrtn_POJO data);

    @Headers({"content-type:application/json",
            "x-karza-key:vN5ojXUenlY3QSzX"})
    @POST("pan")
    Call<PAN_MODEL> panapi(@Body PAN_POJO data);

    @Headers({"content-type:application/json",
            "x-karza-key:vN5ojXUenlY3QSzX"})
    @POST("voter")
    Call<VOTER_MODEL> voterapi(@Body VOTER_POJO data);

    @Headers({"content-type:application/json",
            "x-karza-key:vN5ojXUenlY3QSzX"})
    @POST("passport-verification")
    Call<PASSPORT_MODEL> passportverificationapi(@Body PASSPORT_POJO data);

    @Headers({"content-type:application/json",
            "x-karza-key:vN5ojXUenlY3QSzX"})
    @POST("dl")
    Call<DL_MODEL> dl(@Body DL_POJO data);

    @Headers({"content-type:application/json",
            "x-karza-key:vN5ojXUenlY3QSzX"})
    @POST("aadhaar-consent")
    Call<AADHAR_CONSENT_MODEL> adharconsentapi(@Body AADHAR_CONSENT_POJO data);

    @Headers({"content-type:application/json",
            "x-karza-key:vN5ojXUenlY3QSzX"})
    @POST("get-aadhaar-otp")
    Call<Aadhaarverification_MODEL> aadhaarverification(@Body Aadhaarverification_POJO data);

    @Headers({"content-type:application/json",
            "x-karza-key:vN5ojXUenlY3QSzX"})
    @POST("get-aadhaar-file")
    Call<Aadhar_OTP_Verify_MODEL> getadharfile_otp(@Body Aadhar_OTP_Verify_POJO data);

    @POST("broker_profile_update")
    Call<Broker_profile_update_MODEL> broker_profile_update(@Body Broker_profile_update_POJO data);


    @POST("broker_profile_details")
    Call<Broker_profile_details_MODEL> broker_profile_details(@Body Broker_profile_details_POJO data);

    @POST("store_data_for_MLloan")
    Call<Store_data_for_MLloan_MODEL> store_data_for_MLloan(@Body Store_data_for_MLloan_POJO data);

    @Multipart
    @POST("uploadfile.php")
    Call<String> uploadImage(@Part MultipartBody.Part file, @Part("filename") RequestBody name
    );

    @POST("add_scratchcard")
    Call<Add_scratchcard_MODEL> add_scratchcard(@Body Add_scratchcard_POJO data);

    @POST("view_scratchcard")
    Call<View_scratchcard_MODEL> view_scratchcard(@Body View_scratchcard_POJO data);

    @POST("update_scratchcard")
    Call<Update_scratchcard_MODEL> update_scratchcard(@Body Update_scratchcard_POJO data);

    @POST("wallet_balance")
    Call<Wallet_balance_MODEL> wallet_balance(@Body Wallet_balance_POJO data);

    @POST("wallethistory")
    Call<Wallethistory_MODEL> wallethistory(@Body Wallethistory_POJO data);


    @POST("draftview")
    Call<Draftview_MODEL> draftview(@Body Draftview_POJO data);

    @POST("notification")
    Call<Notification_MODEL> notification(@Body Notification_POJO data);


    @Multipart
    @POST("MLIDproof_upload")
    Call<MLIDproof_upload_MODEL> MLIDproof_upload(@Part("cust_code") RequestBody cust_code,
                                                  @Part("proof_type") RequestBody proof_type,
                                                  @Part("idproof_no") RequestBody idproof_no,
                                                  @Part("name") RequestBody name,
                                                  @Part("reltn_name") RequestBody reltn_name,
                                                  @Part("age") RequestBody age,
                                                  @Part("gender") RequestBody gender,
                                                  @Part("dob") RequestBody dob,
                                                  @Part("doi") RequestBody doi,
                                                  @Part("bloodgrp") RequestBody bloodgrp,
                                                  @Part("disptchfrmsrc") RequestBody disptchfrmsrc,
                                                  @Part MultipartBody.Part idproof_file_front,
                                                  @Part MultipartBody.Part idproof_file_back);


    @Multipart
    @POST("MLAddproof_upload")
    Call<MLAddproof_upload_MODEL> MLAddproof_upload(@Part("cust_code") RequestBody cust_code,
                                                    @Part("proof_type") RequestBody proof_type,
                                                    @Part MultipartBody.Part idproof_file_front,
                                                    @Part MultipartBody.Part idproof_file_back);


    @Multipart
    @POST("MLBnkproof_upload")
    Call<MLBnkproof_upload_MODEL> MLBnkproof_upload(@Part("custcode") RequestBody custcode,
                                                    @Part MultipartBody.Part bankproof_file);


    @Multipart
    @POST("MLEmpproof_upload")
    Call<MLEmpproof_upload_MODEL> MLEmpproof_upload(@Part("custcode") RequestBody custcode,
                                                    @Part("emp_type") RequestBody emp_type,
                                                    @Part MultipartBody.Part bankstmt_file,
                                                    @Part MultipartBody.Part salaryslip_file,
                                                    @Part MultipartBody.Part itr_file);


    @POST("Get_ML_SubCat_List")
    Call<Get_ML_SubCat_List_MODEL> Get_ML_SubCat_List(@Body Get_ML_SubCat_List_POJO data);


    @POST(" banner_list1")
    Call<Banner_list_MODEL> banner_list1(@Body Banner_POJO data);

    @POST(" category_brand_wise")
    Call<Category_brand_wise_MODEL> category_brand_wise(@Body Category_brand_wise_POJO data);


    @POST("GetCatproduct")
    Call<GetCatproductModel> getCatproduct(@Body GetCatproductPojo data);

    @POST("product_details")
    Call<Product_details_Model> product_Details(@Body Product_details_POJO data);

    @POST(" pincode_list")
    Call<Pincode_list_MODEL> pincode_list(@Body Pincode_list_POJO data);

    @POST("calculation_data")
    Call<Calculation_data_MODEL> calculation_data(@Body Calculation_data_POJO data);

    @POST("get_wallet_details")
    Call<Get_wallet_details_MODEL> get_wallet_details(@Body Get_wallet_details_POJO data);

    @POST("loan_calculation")
    Call<Loan_calculation_Model> loan_calculation(@Body Loan_calculation_POJO data);

    @POST("orders")
    Call<RazorpayOrderResponse> createNewOrderID(@Body RazorpayOrderPojo data);

    @POST("update_wallet_credit")
    Call<Update_wallet_credit_MODEL> update_wallet_credit(@Body Update_wallet_credit_POJO data);

    @POST("send_otp_to_user")
    Call<Send_otp_to_user_DEALER_MODEL> send_otp_to_user(@Body Send_otp_to_user_POJO data);


    @POST(" verify_mobile_number")
    Call<Verify_mobile_number_MODEL> verify_mobile_number(@Body Verify_mobile_number_POJO data);


    @POST("get_status")
    Call<Get_loan_status_MODEL> get_status(@Body Get_loan_status_POJO data);


    @POST(" get_voterid_details")
    Call<Get_voterid_details_MODEL> get_voterid_details(@Body Get_voterid_details_POJO data);


    @Multipart
    @POST("store_panid_details")
    Call<Store_panid_details_MODEL> store_panid_details(@Part("brand") RequestBody brand,
                                                        @Part("cust_code") RequestBody cust_code,
                                                        @Part("pan_no") RequestBody pan_no,
                                                        @Part("pan_name") RequestBody pan_name,
                                                        @Part MultipartBody.Part panid);

    @Multipart
    @POST("Store_Other_Document_Details")
    Call<Store_Other_Document_Details_MODEL> Store_Other_Document_Details(@Part("brand") RequestBody brand,
                                                                          @Part("cust_code") RequestBody cust_code,
                                                                          @Part MultipartBody.Part utility,
                                                                          @Part MultipartBody.Part appltn,
                                                                          @Part MultipartBody.Part margin,
                                                                          @Part MultipartBody.Part income,
                                                                          @Part MultipartBody.Part bank);

    @POST(" profile_data")
    Call<Profile_data_MODEL> profile_data(@Body Profile_data_POJO data);


    @POST(" fetch_customer_data")
    Call<Fetch_customer_data_MODEL> fetch_customer_data(@Body Fetch_customer_data_POJO data);

    @POST("submit_customer_application")
    Call<Submit_customer_application_MODEL> submit_customer_application(@Body Submit_customer_application_POJO data);


    @POST("Dealer_Subuser_insert")
    Call<Dealer_Subuser_insert_MODEL> Dealer_Subuser_insert(@Body Dealer_Subuser_insert_POJO data);

    @POST("Dealer_Subuser_fetch")
    Call<Dealer_Subuser_fetch_MODEL> Dealer_Subuser_fetch(@Body Dealer_Subuser_fetch_POJO data);


    @POST("Dealer_Subuser_action")
    Call<Dealer_Subuser_action_MODEL> Dealer_Subuser_action(@Body Dealer_Subuser_action_POJO data);

    @POST("Dealer_Subuser_edit")
    Call<Dealer_Subuser_edit_MODEL> Dealer_Subuser_edit(@Body Dealer_Subuser_edit_POJO data);

    @POST("get_wallet_txn")
    Call<Get_wallet_txn_MODEL> get_wallet_txn(@Body Get_wallet_txn_POJO data);

    @POST(" fetch_application_list")
    Call<Fetch_application_list_MODEL> fetch_application_list(@Body Fetch_application_list_POJO data);

    @POST(" profile_update")
    Call<Profile_Update_DEALER_MODEL> profile_update(@Body Profile_Update_DEALER_POJO data);


    @Multipart
    @POST("store_voterid_front_details")
    Call<store_voterid_details_MODEL> store_voterid_front_details(@Part("brand") RequestBody brand,
                                                                  @Part("cust_code") RequestBody cust_code,
                                                                  @Part("epic_no") RequestBody epic_no,
                                                                  @Part("name") RequestBody name,
                                                                  @Part("name_v1") RequestBody name_v1,
                                                                  @Part("rln_name") RequestBody rln_name,
                                                                  @Part("rln_name_v1") RequestBody rln_name_v1,
                                                                  @Part("rln_type") RequestBody rln_type,
                                                                  @Part("gender") RequestBody gender,
                                                                  @Part("age") RequestBody age,
                                                                  @Part("dob") RequestBody dob,
                                                                  @Part("house_no") RequestBody house_no,
                                                                  @Part("district") RequestBody district,
                                                                  @Part("state") RequestBody state,
                                                                  @Part MultipartBody.Part voterid_front);


    @Multipart
    @POST("store_voterid_back_details")
    Call<Store_voterid_back_details_MODEL> store_voterid_back_details(@Part("cust_code") RequestBody cust_code,
                                                                      @Part MultipartBody.Part voterid_back,
                                                                      @Part("brand") RequestBody brand);

    @Multipart
    @POST("Store_Utility_Document_Details")
    Call<Store_Utility_Document_Details_MODEL> Store_Utility_Document_Details(@Part("brand") RequestBody brand,
                                                                              @Part("cust_code") RequestBody cust_code,
                                                                              @Part MultipartBody.Part utility);


    @Multipart
    @POST("Store_Margin_Document_Details")
    Call<Store_Margin_Document_Details_MODEL> Store_Margin_Document_Details(@Part("brand") RequestBody brand,
                                                                            @Part("cust_code") RequestBody cust_code,
                                                                            @Part MultipartBody.Part margin);

    @Multipart
    @POST("Store_App_Document_Details")
    Call<Store_App_Document_Details_MODEL> Store_App_Document_Details(@Part("brand") RequestBody brand,
                                                                      @Part("cust_code") RequestBody cust_code,
                                                                      @Part MultipartBody.Part appltn);

    @Multipart
    @POST("Store_Income_Document_Details")
    Call<Store_Income_Document_Details_MODEL> Store_Income_Document_Details(@Part("brand") RequestBody brand,
                                                                            @Part("cust_code") RequestBody cust_code,
                                                                            @Part MultipartBody.Part income);

    @Multipart
    @POST("Store_Land_Document_Details")
    Call<Store_Land_Document_Details_MODEL> Store_Land_Document_Details(@Part("brand") RequestBody brand,
                                                                        @Part("cust_code") RequestBody cust_code,
                                                                        @Part MultipartBody.Part land);

    @Multipart
    @POST("Store_Bank_Document_Details")
    Call<Store_Bank_Document_Details_MODEL> Store_Bank_Document_Details(@Part("brand") RequestBody brand,
                                                                        @Part("cust_code") RequestBody cust_code,
                                                                        @Part MultipartBody.Part bank);


    @Multipart
    @POST("MLIDproofFrontUpload")
    Call<MLIDproofFrontUpload_MODEL> MLIDproofFrontUpload(@Part("cust_code") RequestBody cust_code,
                                                          @Part("proof_type") RequestBody proof_type,
                                                          @Part("idproof_no") RequestBody idproof_no,
                                                          @Part("name") RequestBody name,
                                                          @Part("reltn_name") RequestBody reltn_name,
                                                          @Part("age") RequestBody age,
                                                          @Part("gender") RequestBody gender,
                                                          @Part("dob") RequestBody dob,
                                                          @Part("doi") RequestBody doi,
                                                          @Part("bloodgrp") RequestBody bloodgrp,
                                                          @Part("disptchfrmsrc") RequestBody disptchfrmsrc,
                                                          @Part MultipartBody.Part idproof_file_front);

    @Multipart
    @POST("MLIDproofBackUpload")
    Call<MLIDproofBackUpload_MODEL> MLIDproofBackUpload(@Part("cust_code") RequestBody cust_code,
                                                        @Part("proof_type") RequestBody proof_type,
                                                        @Part MultipartBody.Part idproof_file_back);

    @POST("getusercontest")
    Call<Getusercontest_MODEL> getusercontest(@Body Getusercontest_POJO data);

    @GET("getcontestresult")
    Call<Getcontestresult_MODEL> getcontestresult();


    @POST("MLDoc1_upload")
    Call<MLDoc1_upload_MODEL> MLDoc1_upload(@Body MLDoc1_upload_POJO data);

    @POST("MLDoc3_upload")
    Call<MLDoc3_upload_MODEL> MLDoc3_upload(@Body MLDoc3_upload_POJO data);


    @POST("dealer_logout")
    Call<Dealer_logout_MODEL> dealer_logout(@Body Dealer_logoutPOJO data);

    @POST("login_act")
    Call<Login_act_MODEL> login_act(@Body Login_actPOJO data);

    @Headers({"content-type:application/json",
            "x-karza-key:vN5ojXUenlY3QSzX"})
    @POST("bankacc-verification")
    Call<Bankacc_verification_MODEL> bankacc_verification(@Body Bankacc_verification_POJO data);

    @POST("resend_login_otp")
    Call<Resend_login_otp_MODEL> resend_login_otp(@Body Resend_login_otp_POJO data);


    @POST("Resend_otp_to_user")
    Call<Resend_otp_to_user_MODEL> Resend_otp_to_user(@Body Resend_otp_to_user_POJO data);


    @POST("Resend_verify_mobile_number")
    Call<Resend_verify_mobile_number_MODEL> Resend_verify_mobile_number(@Body Resend_verify_mobile_number_POJO data);

    @POST("broker_bank_details_update")
    Call<Broker_bank_details_update_MODEL> broker_bank_details_update(@Body Broker_bank_details_update_POJO data);

    @Multipart
    @POST("dealerbank_Details_Update")
    Call<Dealer_bank_update_MODEL> dealerbank_Details_Update(@Part("user_code") RequestBody user_code,
                                                             @Part("acc_no") RequestBody acc_no,
                                                             @Part("acc_holdr_name") RequestBody acc_holdr_name,
                                                             @Part("ifsc") RequestBody ifsc,
                                                             @Part("branch") RequestBody branch,
                                                             @Part MultipartBody.Part chackimage);

    @POST("Send_MLData_to_ICICI")
    Call<Send_MLData_to_ICICI_MODEL> Send_MLData_to_ICICI(@Body Send_MLData_to_ICICI_POJO data);


    @POST("getqrstatus")
    Call<Dealer_QR_MODEL> Get_QR_Status(@Body QR_CODE_POJO data);

    @POST("updateqrstatus")
    Call<Dealer_QR_MODEL> Set_QR_Status(@Body QR_CODE_POJO data);

    @POST("totalqrcount")
    Call<QR_ScannedList_MODEL> Get_Total_Count(@Body QR_CODE_POJO data);

    @POST("getwpqrstatus")
    Call<WhatsAppStatusList_MODEL> Get_WhatsApp_Status(@Body Dealer_CODE_POJO data);


    @POST("GoatAadharvalidation")
    Call<GoatAdharvalidationResponseDTO> GoatAadharvalidation(@Body GoatAadharvalidation_POJO data);
    @Multipart
    @POST("Dealer_adhar_molldoc")
    Call<Dealer_adhar_molldoc_MODEL> Dealer_adhar_molldoc(@Part("user_code") RequestBody user_code,
                                                          @Part MultipartBody.Part adharimage);

    @Multipart
    @POST("Dealer_pan_molldoc")
    Call<Dealer_pan_molldoc_MODEL> Dealer_pan_molldoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part panimage);


    @Multipart
    @POST("Dealer_bank_molldoc")
    Call<Dealer_bank_molldoc_MODEL> Dealer_bank_molldoc(@Part("user_code") RequestBody user_code,
                                                        @Part MultipartBody.Part bankimage);

    @Multipart
    @POST("Dealer_bank_molldoc")
    Call<Bank_Upload_MODEL> Dealer_Bank_File_Upload(@Part("user_code") RequestBody user_code,
                                                    @Part MultipartBody.Part bankimage);


    @POST("all_POs")
    Call<all_POs_MODEL> all_POs(@Body all_POs_POJO data);

    @POST("top_five_POs")
    Call<top_five_POs_MODEL> top_five_POs(@Body top_five_POs_POJO data);

    @POST("Po_add")
    Call<Po_add_MODEL> Po_add(@Body Po_add_POJO data);

    //supplychain
    @Multipart
    @POST("DealerSelfieDoc")
    Call<DealerSelfieDoc_MODEL> DealerSelfieDoc(@Part("user_code") RequestBody user_code,
                                                    @Part MultipartBody.Part selfie);


    @Multipart
    @POST("DealerAadharFrontDoc")
    Call<DealerAadharFrontDoc_MODEL> DealerAadharFrontDoc(@Part("user_code") RequestBody user_code,
                                                @Part MultipartBody.Part adharimage);


    @Multipart
    @POST("DealerAdharBackDoc")
    Call<DealerAdharBackDoc_MODEL> DealerAdharBackDoc(@Part("user_code") RequestBody user_code,
                                                          @Part MultipartBody.Part adharimage);
//
    @Multipart
    @POST("DealerPanDoc")
    Call<DealerPanDoc_MODEL> DealerPanDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part panimage);
    @Multipart
    @POST("DealerBUSINESSPROOFDoc")
    Call<DealerBUSINESSPROOFDoc_MODEL> DealerBUSINESSPROOFDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part BUSINESS_PROOF);
    @Multipart
    @POST("DealerUDCDoc")
    Call<DealerUDCDoc_MODEL> DealerUDCDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part UDC);
    @Multipart
    @POST("DealerAGREEMENTDoc")
    Call<DealerAGREEMENTDoc_MODEL> DealerAGREEMENTDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part AGREEMENT);
    @Multipart
    @POST("DealerESCROWDoc")
    Call<DealerESCROWDoc_MODEL> DealerESCROWDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part ESCROW);
    @Multipart
    @POST("DealerDISB_BANKDoc")
    Call<DealerDISB_BANKDoc_MODEL> DealerDISB_BANKDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part bankimage);
    @Multipart
    @POST("DealerLEDGERDoc")
    Call<DealerLEDGERDoc_MODEL> DealerLEDGERDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part LEDGER);
    @Multipart
    @POST("DealerINVOICEDoc")
    Call<DealerINVOICEDoc_MODEL> DealerINVOICEDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part INVOICE);
    @Multipart
    @POST("DealerITRDoc")
    Call<DealerITRDoc_MODEL> DealerITRDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part ITR);
    @Multipart
    @POST("DealerTDSDoc")
    Call<DealerTDSDoc_MODEL> DealerTDSDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part TDS);

    @Multipart
    @POST("DealerGSTDoc")
    Call<DealerGSTDoc_MODEL> DealerGSTDoc(@Part("user_code") RequestBody user_code,
                                          @Part MultipartBody.Part GST);



    @POST("GoatAadharvalidation1")
    Call<GoatAadharvalidation1_MODEL> GoatAadharvalidation1(@Body GoatAadharvalidation1_POJO data);
}
