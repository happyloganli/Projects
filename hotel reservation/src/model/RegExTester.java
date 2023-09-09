package model;

import java.util.regex.Pattern;

public class RegExTester {

    public RegExTester() {
    }
    public Boolean emailTester(String email){
        String emailRegex = "^(.+)@(.+)\\.(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }


}
