package hospital.hospitalqueue;

import java.text.SimpleDateFormat;
import java.util.Calendar;



public class DateThai {

    public static String getDate(boolean flag){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf;

        if(flag) {
            sdf = new SimpleDateFormat("dd-MM-yyyy");
            String strDate = sdf.format(c.getTime());
            String[] temp2 = strDate.split("-");
            int year = (Integer.parseInt(temp2[2])+543);
            return  temp2[0]+"-"+temp2[1]+"-"+year;
        }
        else {
            sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String strDate = sdf.format(c.getTime());
            String[] temp = strDate.split(" ");
            String[] temp2 = temp[0].split("-");
            int year = (Integer.parseInt(temp2[2])+543);
            return  temp2[0]+"-"+temp2[1]+"-"+year+" "+temp[1];
        }


    }

}
