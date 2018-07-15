package com.example.shihualu.gourmettest.Module;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {
    public static void saveObj(Context context, UserData obj){
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            fos = context.openFileOutput("userData", Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(obj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(os!=null)
                    os.close();
                if(fos!=null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //return empty UserData instance if first time open
    //otherwise return saved UserData
    public static UserData loadObj(Context context){
        FileInputStream fis = null;
        ObjectInputStream is = null;
        UserData userData = null;

        try {
            fis = context.openFileInput("userData");
            is = new ObjectInputStream(fis);
            userData = (UserData) is.readObject();

        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            //userData =  new UserData();
        } catch (IOException e) {
            e.printStackTrace();
            //userData = new UserData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //userData = new UserData();
        }finally {
            try {
                if(is!=null)
                    is.close();
                if(fis!=null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userData;
    }

}