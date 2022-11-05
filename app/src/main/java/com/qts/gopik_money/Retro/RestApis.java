package com.qts.gopik_money.Retro;

import com.qts.gopik_money.Model.*;
import com.qts.gopik_money.Model.Dealer_Add_SubUser_POJO;
import com.qts.gopik_money.Pojo.*;
import com.qts.gopik_money.Pojo.LoanPoAllDetails_POJO;
import com.qts.gopik_money.Pojo.Po_add_POJO;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
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
    @POST("aadhaar-verification")
    Call<Aadhaarverification_MODEL> aadhaarverification(@Body Aadhaarverification_POJO data);

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
            "x-karza-key:RossOSh7QOPxo9b"})
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
    @POST("DealerSelfieDoc")
    Call<DealerSelfieDoc_MODEL> GetDealerSelfieDoc(@Body ShopkeeperdoctoFinance_POJO data);



    @Multipart
    @POST("DealerESCROWDoc")
    Call<DealerESCROWDoc_MODEL> DealerESCROWDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part ESCROW);



    @POST("po_all_details")
    Call<Po_all_details_MODEL> po_all_details(@Body Po_all_details_POJO data);

    @POST("update_po_status")
    Call<Update_po_status_MODEL> update_po_status(@Body Update_po_status_POJO data);


    @Multipart
    @POST("podisverslreportupdate")
    Call<podisverslreportupdate_MODEL> podisverslreportupdate(@Part("po_id") RequestBody po_id,
                                          @Part MultipartBody.Part dealer_final_report);

    @POST("dealer_doc_confirm")
    Call<dealer_doc_confirm_MODEL> dealer_doc_confirm(@Body Dealer_doc_confirm_POJO data);

    @POST("loanlimitdetails")
    Call<LoanLimit_Details_MODEL> loanlimitdetails(@Body Loanlimitdetails_POJO data);

    @POST("dealerpoloans")
    Call<DealerPoLoans_Model> dealerpoloans(@Body DealerPoLoans_POJO data);

    @POST("loanpoalldetails")
    Call<LoanPoAllDetails_MODEL> loanpoalldetails(@Body LoanPoAllDetails_POJO data);

    @POST("GoatAadharvalidation1")
    Call<GoatAadharvalidation1_MODEL> GoatAadharvalidation1(@Body GoatAadharvalidation1_POJO data);

    @POST("qrsummary")
    Call<QRSummaryModel> Get_Total_Summary(@Body QR_CODE_POJO data);

    @POST("mod_appr_po_status")
    Call<mod_appr_po_status_Model> mod_appr_po_status(@Body mod_appr_po_status_POJO data);



    @Multipart
    @POST("Po_img_add")
    Call<Po_img_add_MODEL> Po_img_add(@Part("po_id") RequestBody po_id,
                                                              @Part MultipartBody.Part po_img);


    @POST("po_products")
    Call<Po_products_Model> po_products(@Body Po_products_POJO data);

    @POST("DealerdoctoFinance")
    Call<DealerdoctoFinance_MODEL> DealerdoctoFinance(@Body DealerdoctoFinance_POJO user_code);
    @POST("ShopkeeperdoctoFinance")
    Call<DealerdoctoFinance_MODEL> ShopkeeperdoctoFinance(@Body DealerdoctoFinance_POJO user_code);
    @POST("SubdealerdoctoFinance")
    Call<DealerdoctoFinance_MODEL> SubDealerdoctoFinance(@Body DealerdoctoFinance_POJO user_code);

    @POST("SetCreditLimitForDealer")
    Call<SetCreditLimitForDealer_MODEL> SetCreditLimitForDealer(@Body SetCreditLimitForDealer_POJO user_code);
    @POST("SetCreditLimitForSubdealer")
    Call<SetCreditLimitForDealer_MODEL> SetCreditLimitForSubDealer(@Body SetCreditLimitForDealer_POJO user_code);


    @POST("shopkeeper_profile_details")
    Call<Shopkeeper_profile_details_MODEL> shopkeeper_profile_details(@Body Shopkeeper_profile_details_POJO data);


    @POST("shopkeeper_profile_update")
    Call<Shopkeeper_profile_update_MODEL> shopkeeper_profile_update(@Body Shopkeeper_profile_update_POJO data);

    @POST("shopkeeper_bank_details_update")
    Call<Shopkeeper_bank_details_update_MODEL> shopkeeper_bank_details_update(@Body Shopkeeper_bank_details_update_POJO data);


    @Multipart
    @POST("mymall_shopkeeper")
    Call<Mymall_shopkeeper_MODEL>mymall_shopkeeper(@Part("user_code") RequestBody user_code,
                                                   @Part("tax_invoice_no") RequestBody tax_invoice_no,
                                                   @Part("tax_invoice_price") RequestBody tax_invoice_price,
                                                   @Part MultipartBody.Part tax_invoice_image,
                                                   @Part("timestamp") RequestBody timestamp);


    @POST("shopkeeperpo_data")
    Call<Shopkeeperpo_data_MODEL> shopkeeperpo_data(@Body Shopkeeperpo_data_POJO data);

    @POST("shopkeeperpo_loan_limit_details")
    Call<Shopkeeperpo_loan_limit_details_data_MODEL> shopkeeperpo_loan_limit_details(@Body Shopkeeperpo_loan_limit_details_POJO data);

    @POST("shopkeeperpo_data_list")
    Call<Shopkeeperpo_data_MODEL_datalist> shopkeeperpo_data_list(@Body Shopkeeperpo_data_list_POJO data);

    @POST("shopkeeperpo_loan")
    Call<Shopkeeperpo_loan_MODEL> shopkeeperpo_loan(@Body Shopkeeperpo_loan_POJO data);

    @POST("shopkeeperpo_loan_alldetail")
    Call<Shopkeeperpo_loan_alldetail_MODEL> shopkeeperpo_loan_alldetail(@Body Shopkeeperpo_loan_alldetail_POJO data);



    @Multipart
    @POST("shopkeeperpo_disversal")
    Call<Shopkeeperpo_disversal_MODEL>shopkeeperpo_disversal(@Part("po_id") RequestBody po_id,
                                                   @Part MultipartBody.Part shopkeeper_po_report
                                           );


    //subdealer

    @POST("subdealeradd")
    Call<Dealer_Subuser_insert_MODEL> Subdealeradd(@Body Dealer_Add_SubUser_POJO data);


    @POST("getsubdealertopfivepo")
    Call<SubDealerPOListResponseModel> GetSubDealerList(@Body PO_POJO data);


    @POST("subdealer_po_add")
    Call<Po_add_MODEL> subdealer_po_add(@Body Po_add_POJO data);


    @POST("getsubdealerowntopfivepo")
    Call<top_five_POs_MODEL> getsubdealerowntopfivepo(@Body top_five_POs_POJO data);


    @POST("dealer_po_reject")
    Call<RejectPOModel> RejectPO(@Body PO_ID_POJO data);

    @POST("getsubdealerallpo")
    Call<SubDealerPOListResponseModel> GetSubDealerAllPOData(@Body PO_POJO data);

    @Multipart
    @POST("dealer_po_approve")
    Call<RejectPOModel> ApprovePO(
            @Part("po_id") RequestBody po_id,
            @Part("invoice_no") RequestBody invoice_no,
            @Part("estm_amount") RequestBody estm_amount,
            @Part MultipartBody.Part invoice);

    @Multipart
    @POST("subdealer_po_invoice_img_add")
    Call<RejectPOModel> ModifyImagePODocUpload(
            @Part("po_id") RequestBody po_id,
            @Part MultipartBody.Part invoice);

    @POST("subdealer_po_all_details")
    Call<SubDealerPODetailsResponseModel> SubDealerPODetails(@Body POID_POJO data);

    @POST("dealer_po_modify")
    Call<RejectPOModel> ModifyPO(@Body PO_MODIFY_POJO data);

    @POST("getsubdealerownallpo")
    Call<all_POs_MODEL> GetSubDealerAllPODataOne(@Body PO_POJO data);

    @Multipart
    @POST("subdealer_po_img_add")
    Call<Po_img_add_MODEL> SubDealerPo_img_add(@Part("po_id") RequestBody po_id,
                                               @Part MultipartBody.Part po_img);

    @POST("subdealer_po_all_details")
    Call<Po_all_details_MODEL> subdealer_po_all_details(@Body Po_all_details_POJO data);

    @POST("subdealer_po_reject")
    Call<mod_appr_po_status_Model> subdealer_po_reject(@Body PO_ID_POJO data);

    @POST("subdealer_po_approve")
    Call<mod_appr_po_status_Model> subdealer_po_approve(@Body PO_ID_POJO data);

    //shopkeeper document


    @POST("SetCreditLimitForShopkeeper")
    Call<SetCreditLimitForShopkeeperModel> SetCreditLimitForShopkeeper(@Body SetCreditLimitForShopkeeper_POJO data);



    @POST("SubdealerSelfieDoc")
    Call<DealerSelfieDoc_MODEL> GetSubDealerSelfieDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("SubdealerSelfieDoc")
    Call<DealerSelfieDoc_MODEL> DealerSubSelfieDoc(@Part("user_code") RequestBody user_code,
                                                   @Part MultipartBody.Part selfie);

    @Multipart
    @POST("DealerAadharFrontDoc")
    Call<DealerAadharFrontDoc_MODEL> DealerAadharFrontDoc(@Part("user_code") RequestBody user_code,
                                                          @Part MultipartBody.Part adharimage);

    @POST("DealerAadharFrontDoc")
    Call<DealerAadharFrontDoc_MODEL> GetDealerAadharFrontDoc(@Body ShopkeeperdoctoFinance_POJO data);


    @Multipart
    @POST("SubdealerAadharFrontDoc")
    Call<DealerAadharFrontDoc_MODEL> SubDealerAadharFrontDoc(@Part("user_code") RequestBody user_code,
                                                             @Part MultipartBody.Part adharimage);

    @POST("SubdealerAadharFrontDoc")
    Call<DealerAadharFrontDoc_MODEL> GetSubDealerAadharFrontDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("DealerAdharBackDoc")
    Call<DealerAdharBackDoc_MODEL> DealerAdharBackDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part adharimage);

    @POST("DealerAdharBackDoc")
    Call<DealerAdharBackDoc_MODEL> GetDealerAdharBackDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("SubdealerAdharBackDoc")
    Call<DealerAdharBackDoc_MODEL> SubDealerAdharBackDoc(@Part("user_code") RequestBody user_code,
                                                         @Part MultipartBody.Part adharimage);

    @POST("SubdealerAdharBackDoc")
    Call<DealerAdharBackDoc_MODEL> GetSubDealerAdharBackDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("DealerPanDoc")
    Call<DealerPanDoc_MODEL> DealerPanDoc(@Part("user_code") RequestBody user_code,
                                          @Part MultipartBody.Part panimage);

    @POST("DealerPanDoc")
    Call<DealerPanDoc_MODEL> GetDealerPanDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("SubdealerPanDoc")
    Call<DealerPanDoc_MODEL> SubDealerPanDoc(@Part("user_code") RequestBody user_code,
                                             @Part MultipartBody.Part panimage);

    @POST("SubdealerPanDoc")
    Call<DealerPanDoc_MODEL> GetSubDealerPanDoc(@Body ShopkeeperdoctoFinance_POJO data);


    //shopkeeper apis


    @Multipart
    @POST("ShopkeeperSelfieDoc")
    Call<DealerSelfieDoc_MODEL> ShopkeeperSelfieDoc(@Part("user_code") RequestBody user_code,
                                                    @Part MultipartBody.Part selfie);


    @POST("ShopkeeperSelfieDoc")
    Call<DealerSelfieDoc_MODEL> GetShopkeeperSelfieDocImage(@Body ShopkeeperdoctoFinance_POJO data);


    @Multipart
    @POST("ShopkeeperAadharFrontDoc")
    Call<DealerAadharFrontDoc_MODEL> ShopkeeperAadharFrontDoc(@Part("user_code") RequestBody user_code,
                                                              @Part MultipartBody.Part adharimage);


    @POST("ShopkeeperAadharFrontDoc")
    Call<DealerAadharFrontDoc_MODEL> GetShopkeeperAadharFrontDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("ShopkeeperAdharBackDoc")
    Call<DealerAdharBackDoc_MODEL> ShopkeeperAdharBackDoc(@Part("user_code") RequestBody user_code,
                                                          @Part MultipartBody.Part adharimage);

    @POST("ShopkeeperAdharBackDoc")
    Call<DealerAdharBackDoc_MODEL> GetShopkeeperAdharBackDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("ShopkeeperPanDoc")
    Call<DealerPanDoc_MODEL>ShopkeeperPanDoc(@Part("user_code") RequestBody user_code,
                                             @Part MultipartBody.Part panimage);


    @POST("ShopkeeperPanDoc")
    Call<DealerPanDoc_MODEL> GetShopkeeperPanDoc(@Body ShopkeeperdoctoFinance_POJO data);


    @Multipart
    @POST("ShopkeeperBUSINESSPROOFDoc")
    Call<DealerBUSINESSPROOFDoc_MODEL>ShopkeeperBUSINESSPROOFDoc(@Part("user_code") RequestBody user_code,
                                                                 @Part MultipartBody.Part BUSINESS_PROOF);


    @POST("ShopkeeperBUSINESSPROOFDoc")
    Call<DealerBUSINESSPROOFDoc_MODEL> GetShopkeeperBUSINESSPROOFDoc(@Body ShopkeeperdoctoFinance_POJO data);


    @Multipart
    @POST("ShopkeeperUDCDoc")
    Call<DealerUDCDoc_MODEL> ShopkeeperUDCDoc(@Part("user_code") RequestBody user_code,
                                              @Part MultipartBody.Part UDC);


    @POST("ShopkeeperUDCDoc")
    Call<DealerUDCDoc_MODEL> GetShopkeeperUDCDoc(@Body ShopkeeperdoctoFinance_POJO data);


    @Multipart
    @POST("ShopkeeperAGREEMENTDoc")
    Call<DealerAGREEMENTDoc_MODEL> ShopkeeperAGREEMENTDoc(@Part("user_code") RequestBody user_code,
                                                          @Part MultipartBody.Part AGREEMENT);


    @POST("ShopkeeperAGREEMENTDoc")
    Call<DealerAGREEMENTDoc_MODEL> GetShopkeeperAGREEMENTDoc(@Body ShopkeeperdoctoFinance_POJO data);


    @Multipart
    @POST("ShopkeeperESCROWDoc")
    Call<DealerESCROWDoc_MODEL> ShopkeeperESCROWDoc(@Part("user_code") RequestBody user_code,
                                                    @Part MultipartBody.Part ESCROW);


    @POST("ShopkeeperESCROWDoc")
    Call<DealerESCROWDoc_MODEL> GetShopkeeperESCROWDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("ShopkeeperDISB_BANKDoc")
    Call<DealerDISB_BANKDoc_MODEL> ShopkeeperDISB_BANKDoc(@Part("user_code") RequestBody user_code,
                                                          @Part MultipartBody.Part bankimage);


    @POST("ShopkeeperDISB_BANKDoc")
    Call<DealerDISB_BANKDoc_MODEL> GetShopkeeperDISB_BANKDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("ShopkeeperLEDGERDoc")
    Call<DealerLEDGERDoc_MODEL> ShopkeeperLEDGERDoc(@Part("user_code") RequestBody user_code,
                                                    @Part MultipartBody.Part LEDGER);


    @POST("ShopkeeperLEDGERDoc")
    Call<DealerLEDGERDoc_MODEL> GetShopkeeperLEDGERDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("ShopkeeperINVOICEDoc")
    Call<DealerINVOICEDoc_MODEL> ShopkeeperINVOICEDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part INVOICE);


    @POST("ShopkeeperINVOICEDoc")
    Call<DealerINVOICEDoc_MODEL> GetShopkeeperINVOICEDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("ShopkeeperITRDoc")
    Call<DealerITRDoc_MODEL> ShopkeeperITRDoc(@Part("user_code") RequestBody user_code,
                                              @Part MultipartBody.Part ITR);

    @POST("ShopkeeperITRDoc")
    Call<DealerITRDoc_MODEL> GetShopkeeperITRDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("ShopkeeperTDSDoc")
    Call<DealerTDSDoc_MODEL> ShopkeeperTDSDoc(@Part("user_code") RequestBody user_code,
                                              @Part MultipartBody.Part TDS);


    @POST("ShopkeeperTDSDoc")
    Call<DealerTDSDoc_MODEL> GetShopkeeperTDSDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("ShopkeeperGSTDoc")
    Call<DealerGSTDoc_MODEL> ShopkeeperGSTDoc(@Part("user_code") RequestBody user_code,
                                              @Part MultipartBody.Part GST);

    @POST("ShopkeeperGSTDoc")
    Call<DealerGSTDoc_MODEL> GetShopkeeperGSTDoc(@Body ShopkeeperdoctoFinance_POJO data);


    @POST("ShopkeeperdoctoFinance")
    Call<ShopkeeperdoctoFinanceModel> ShopkeeperdoctoFinance(@Body ShopkeeperdoctoFinance_POJO data);



    //document

    @Multipart
    @POST("DealerBUSINESSPROOFDoc")
    Call<DealerBUSINESSPROOFDoc_MODEL> DealerBUSINESSPROOFDoc(@Part("user_code") RequestBody user_code,
                                                              @Part MultipartBody.Part BUSINESS_PROOF);

    @POST("DealerBUSINESSPROOFDoc")
    Call<DealerBUSINESSPROOFDoc_MODEL> GetDealerBUSINESSPROOFDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("SubdealerBUSINESSPROOFDoc")
    Call<DealerBUSINESSPROOFDoc_MODEL> SubDealerBUSINESSPROOFDoc(@Part("user_code") RequestBody user_code,
                                                                 @Part MultipartBody.Part BUSINESS_PROOF);

    @POST("SubdealerBUSINESSPROOFDoc")
    Call<DealerBUSINESSPROOFDoc_MODEL> GetSubDealerBUSINESSPROOFDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("DealerGSTDoc")
    Call<DealerGSTDoc_MODEL> DealerGSTDoc(@Part("user_code") RequestBody user_code,
                                          @Part MultipartBody.Part GST);
    @POST("DealerGSTDoc")
    Call<DealerGSTDoc_MODEL> GetDealerGSTDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("SubdealerGSTDoc")
    Call<DealerGSTDoc_MODEL> SubDealerGSTDoc(@Part("user_code") RequestBody user_code,
                                             @Part MultipartBody.Part GST);
    @POST("SubdealerGSTDoc")
    Call<DealerGSTDoc_MODEL> GetSubDealerGSTDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("DealerAGREEMENTDoc")
    Call<DealerAGREEMENTDoc_MODEL> DealerAGREEMENTDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part AGREEMENT);
    @POST("DealerAGREEMENTDoc")
    Call<DealerAGREEMENTDoc_MODEL> GetDealerAGREEMENTDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("SubdealerAGREEMENTDoc")
    Call<DealerAGREEMENTDoc_MODEL> SubDealerAGREEMENTDoc(@Part("user_code") RequestBody user_code,
                                                         @Part MultipartBody.Part AGREEMENT);
    @POST("SubdealerAGREEMENTDoc")
    Call<DealerAGREEMENTDoc_MODEL> GetSubDealerAGREEMENTDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("DealerUDCDoc")
    Call<DealerUDCDoc_MODEL> DealerUDCDoc(@Part("user_code") RequestBody user_code,
                                          @Part MultipartBody.Part UDC);
    @POST("DealerUDCDoc")
    Call<DealerUDCDoc_MODEL> GetDealerUDCDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("SubdealerUDCDoc")
    Call<DealerUDCDoc_MODEL> SubDealerUDCDoc(@Part("user_code") RequestBody user_code,
                                             @Part MultipartBody.Part UDC);
    @POST("SubdealerUDCDoc")
    Call<DealerUDCDoc_MODEL> GetSubDealerUDCDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("DealerDISB_BANKDoc")
    Call<DealerDISB_BANKDoc_MODEL> DealerDISB_BANKDoc(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part bankimage);
    @Multipart
    @POST("DealerDISB_BANKDoc")
    Call<BANK_DOCUMENT_MODEL> BankDocumentMethod(@Part("user_code") RequestBody user_code,
                                                      @Part MultipartBody.Part bankimage,
                                                      @Part MultipartBody.Part bankimage1,
                                                      @Part MultipartBody.Part bankimage2,
                                                      @Part MultipartBody.Part bankimage3,
                                                      @Part MultipartBody.Part bankimage4,
                                                      @Part MultipartBody.Part bankimage5);
    @POST("DealerDISB_BANKDoc")
    Call<DealerDISB_BANKDoc_MODEL> GetDealerDISB_BANKDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("SubdealerDISB_BANKDoc")
    Call<DealerDISB_BANKDoc_MODEL> SubDealerDISB_BANKDoc(@Part("user_code") RequestBody user_code,
                                                         @Part MultipartBody.Part bankimage);
    @POST("SubdealerDISB_BANKDoc")
    Call<DealerDISB_BANKDoc_MODEL> GetSubDealerDISB_BANKDoc(@Body ShopkeeperdoctoFinance_POJO data);


    @Multipart
    @POST("DealerLEDGERDoc")
    Call<DealerLEDGERDoc_MODEL> DealerLEDGERDoc(@Part("user_code") RequestBody user_code,
                                                @Part MultipartBody.Part LEDGER);
    @POST("DealerLEDGERDoc")
    Call<DealerLEDGERDoc_MODEL> GetDealerLEDGERDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("SubdealerLEDGERDoc")
    Call<DealerLEDGERDoc_MODEL> SubDealerLEDGERDoc(@Part("user_code") RequestBody user_code,
                                                   @Part MultipartBody.Part LEDGER);
    @POST("SubdealerLEDGERDoc")
    Call<DealerLEDGERDoc_MODEL> GetSubDealerLEDGERDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("DealerITRDoc")
    Call<DealerITRDoc_MODEL> DealerITRDoc(@Part("user_code") RequestBody user_code,
                                          @Part MultipartBody.Part ITR);
    @POST("DealerITRDoc")
    Call<DealerITRDoc_MODEL> GetDealerITRDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("SubdealerITRDoc")
    Call<DealerITRDoc_MODEL> SubDealerITRDoc(@Part("user_code") RequestBody user_code,
                                             @Part MultipartBody.Part ITR);
    @POST("SubdealerITRDoc")
    Call<DealerITRDoc_MODEL> GetSubDealerITRDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("DealerTDSDoc")
    Call<DealerTDSDoc_MODEL> DealerTDSDoc(@Part("user_code") RequestBody user_code,
                                          @Part MultipartBody.Part TDS);
    @POST("DealerTDSDoc")
    Call<DealerTDSDoc_MODEL> GetDealerTDSDoc(@Body ShopkeeperdoctoFinance_POJO data);

    @Multipart
    @POST("SubdealerTDSDoc")
    Call<DealerTDSDoc_MODEL> SubDealerTDSDoc(@Part("user_code") RequestBody user_code,
                                             @Part MultipartBody.Part TDS);
    @POST("SubdealerTDSDoc")
    Call<DealerTDSDoc_MODEL> GetSubDealerTDSDoc(@Body ShopkeeperdoctoFinance_POJO data);


    @Multipart
    @POST("DealerINVOICEDoc")
    Call<DealerINVOICEDoc_MODEL> DealerINVOICEDoc(@Part("user_code") RequestBody user_code,
                                                  @Part MultipartBody.Part INVOICE);

    @POST("credit_loan")
    Call<LoanLimit_Details_MODEL> credit_loan(@Body ShopkeeperdoctoFinance_POJO data);

    @POST("check_goat_tagid")
    Call<TagID_MODEL> CheckGoatTagId(@Body TAG_ID_POJO data);

    @POST("Goat_Store_CalculationData")
    Call<GoatFinalCalculateModel> GoatStoreCalculationData(@Body StoreGoatCalculationResponsePOJO data);

    @POST("GetLoan_Calculation_For_Goat")
    Call<GoatAllCalculateModel> GetLoanCalculationForGoat(@Body GoatCalculatePOJO data);

    @POST("GoatAadharvalidation")
    Call<AadharEnterResponseModel> GoatAadharValidation(@Body Aadhar_POJO data);

    @POST("GoatAadharvalidation1")
    Call<TagID_MODEL> GoatAadharValidationNext(@Body AadharNext_POJO data);

    @Multipart
    @POST("goat_produt_image_upload")
    Call<TagID_MODEL> GoatProductImageUpload(@Part("apptn_no") RequestBody mAppNo,
                                             @Part("brand") RequestBody mBrand,
                                             @Part MultipartBody.Part mGoatFraontImage,
                                             @Part MultipartBody.Part mGoatBackImage);

    @POST("goat_verify_mobile_number")
    Call<Verify_mobile_number_MODEL> goat_verify_mobile_number(@Body Verify_mobile_number_POJO data);

    @Multipart
    @POST("Store_Goat_PanidDetails")
    Call<Store_panid_details_MODEL> StoreGoatPanIdDetails(@Part("brand") RequestBody brand,
                                                          @Part("cust_code") RequestBody cust_code,
                                                          @Part("pan_no") RequestBody pan_no,
                                                          @Part("pan_name") RequestBody pan_name,
                                                          @Part MultipartBody.Part panid);

    @Multipart
    @POST("Store_Goat_otherDocDetails")
    Call<TagID_MODEL> StoreGoatOtherDocDetails(@Part("cust_code") RequestBody mAppNo,
                                               @Part("brand") RequestBody mBrand,
                                               @Part MultipartBody.Part mGoatFrontImage,
                                               @Part MultipartBody.Part mGoatSideImage,
                                               @Part MultipartBody.Part mGoatFarmerImage,
                                               @Part MultipartBody.Part mBankImage);


    @POST("FetchGoatAppListthree")
    Call<Fetch_Goat_current_appliation_list_DEALER_MODEL> FetchGoatAppListthree(@Body FetchGoatAppListthree_DEALER_POJO data);


    @POST("FetchGoatApplicationList")
    Call<Fetch_Goat_current_appliation_list_DEALER_MODEL> FetchGoatApplicationList(@Body FetchGoatAppListthree_DEALER_POJO data);

    @POST("GoatKycdetailstore")
    Call<GOATKYCSTORE_MODEL> GoatKycdetailstore(@Body GOATKYCSTOREPOJO data);



    @POST("Goat_Store_CalculationData")
    Call<Calculation_data_MODEL> goat_calculation_data(@Body Hero_Loan_Calculation_Data_POJO data);

    @POST("GetLoan_Calculation_For_Goat")
    Call<HeroLoanRespModel> goat_loan_calculation(@Body Hero_Loan_calculation_POJO data);

    @POST("GetLoan_Calculation_For_Hero")
    Call<HeroLoanRespModel> hero_loan_calculation(@Body Hero_Loan_calculation_POJO data);

    @POST("Hero_Store_CalculationData")
    Call<Calculation_data_MODEL> hero_calculation_data(@Body Hero_Loan_Calculation_Data_POJO data);

    @POST("subdealer_profile_details")
    Call<ProfileDetails_DEALER_MODEL> subdealer_profile_details(@Body ProfileDetails_DEALER_POJO data);

    @POST("subdealerpoloans")
    Call<DealerPoLoans_Model> subdealerpoloans(@Body DealerPoLoans_POJO data);

    @POST("subdealerloanpoalldetails")
    Call<LoanPoAllDetails_MODEL> subdealerloanpoalldetails(@Body LoanPoAllDetails_POJO data);

    @POST("subdealerloanlimitdetails")
    Call<LoanLimit_Details_MODEL> subdealerloanlimitdetails(@Body Loanlimitdetails_POJO data);


    @POST("dealer_invoice_data")
    Call<Dealer_invoice_data_MODEL> dealer_invoice_data_api(@Body Dealer_invoice_data_POJO data);


    @POST("subdealer_invoice_data")
    Call<Dealer_invoice_data_MODEL> subdealer_invoice_data(@Body Dealer_invoice_data_POJO data);


    @POST(" subdealerprofileupdate")
    Call<Profile_Update_DEALER_MODEL> subdealerprofileupdate(@Body Profile_Update_DEALER_POJO data);


    @Multipart
    @POST("subdealerbankdetailupdate")
    Call<Dealer_bank_update_MODEL> subdealerbankdetailupdate(@Part("user_code") RequestBody user_code,
                                                             @Part("acc_no") RequestBody acc_no,
                                                             @Part("acc_holdr_name") RequestBody acc_holdr_name,
                                                             @Part("ifsc") RequestBody ifsc,
                                                             @Part("branch") RequestBody branch,
                                                             @Part MultipartBody.Part chackimage);


    @POST("Dealerbusinessyear")
    Call<LoanLimit_Details_MODEL> Dealerbusinessyear(@Body OEMDetailsPOJO data);

    @POST("Subdealerbusinessyear")
    Call<LoanLimit_Details_MODEL> Subdealerbusinessyear(@Body OEMDetailsPOJO data);

    @POST("DealerDocDelete")
    Call<DealerDocDeleteModel> DealerDocDelete(@Body DealerDocDelete_POJO data);


    @Multipart
    @POST("subdealer_po_img_add")
    Call<podisverslreportupdate_MODEL> subdealer_po_img_add(@Part("po_id") RequestBody po_id,
                                               @Part MultipartBody.Part po_img);
    @Multipart
    @POST("Store_Goat_AddharDetails")
    Call<TagID_MODEL> StoreGoatAadharDocDetails(@Part("brand") RequestBody mBrand,
                                                @Part("apptn_no") RequestBody mApplNo,
                                                @Part MultipartBody.Part mGoatFrontImage,
                                                @Part MultipartBody.Part mGoatSideImage,
                                                @Part MultipartBody.Part mGoatFarmerImage);



    @Multipart
    @POST("poimage_add")
    Call<PoImageAdd_MODEL> poimage_add(@Part("user_code") RequestBody user_code,
                                                @Part MultipartBody.Part po_img,
                                                @Part("type") RequestBody type);
}
