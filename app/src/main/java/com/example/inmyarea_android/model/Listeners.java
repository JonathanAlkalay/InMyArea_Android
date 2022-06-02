package com.example.inmyarea_android.model;

import com.example.inmyarea_android.model.ResponseMessages.GetBusinessesRespMsg;
import com.example.inmyarea_android.model.ResponseMessages.GetAccountResponseMessage;
import com.example.inmyarea_android.model.ResponseMessages.GetAppointmentsRespMsg;
import com.example.inmyarea_android.model.ResponseMessages.MainResponseMessage;
import com.example.inmyarea_android.model.Users.User;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Listeners {

    public static final Listeners instance = new Listeners();
    Model_RetroFit retroFit = new Model_RetroFit();


    public interface logInListener{
        void onComplete(MainResponseMessage data);
    }
    public void logIn(String userName, String passWord, String type, logInListener listener){
        retroFit.logIn(userName, passWord, type, listener);
    }

    public interface createAccountListener {
        void onComplete(MainResponseMessage data);
    }
    public void createAccount(String email, String type, User user, createAccountListener listener) {
        retroFit.createAccount(email, type, user, listener);
    }

    public interface getAccountByEmailListener{
        void onComplete(GetAccountResponseMessage data) throws IOException;
    }
    public void getAccountByEmail(String email, String type, getAccountByEmailListener listener){
        retroFit.getAccountByEmail(email, type, listener);
    }

    public interface getBusinessesByCategoryListener{
        void onComplete(GetBusinessesRespMsg data);
    }
    public void getBusinessesByCategory(String category, getBusinessesByCategoryListener listener){
        retroFit.getBusinessesByCategory(category, listener);
    }

    public interface getAppointmentsByDateListener{
        void onComplete(GetAppointmentsRespMsg data);
    }
    public void AppointmentsByDate(String email,String date, getAppointmentsByDateListener listener){
        retroFit.getAppointmentsByDate(email,date, listener);
    }

    public interface getAppointmentsByUserListener{
        void onComplete(GetAppointmentsRespMsg data);
    }
    public void AppointmentsByUser(String email, getAppointmentsByDateListener listener){
        retroFit.getAppointmentsByUser(email, listener);
    }


    public interface updateAccountDetailsListener{
        void onComplete(MainResponseMessage data) throws URISyntaxException;
    }
    public void updateAccountDetails(String email, String type, User updatedUser, updateAccountDetailsListener listener){
        retroFit.updateBasicAccountDetails(email, type, updatedUser, listener);
    }

    public interface uploadVideoListener{
        void onComplete(MainResponseMessage data);
    }
    public void uploadVideo(File file, String email, uploadVideoListener listener){
        retroFit.uploadVideo(file, email, listener);
    }

    public interface addAppointmentListener {
        void onComplete(MainResponseMessage data);
    }
    public void addAppointment(Appointment appointment, addAppointmentListener listener) {
        retroFit.addAppointment(appointment, listener);
    }

    public interface getAccountsByLocationListener{
        void onComplete(GetBusinessesRespMsg data) throws IOException;
    }
    public void getAccountsByLocation(Double longitude, Double latitude, getAccountsByLocationListener listener){
        retroFit.getAccountsByLocation(longitude, latitude, listener);
    }

    public interface updateAppointmentListener{
        void onComplete(MainResponseMessage data);
    }
    public void updateAppointment(String userEmail, String businessEmail, String date, String time, Appointment updatedAppointment, updateAppointmentListener listener){
        retroFit.updateAppointment(userEmail, businessEmail, date, time, updatedAppointment, listener);
    }


}
