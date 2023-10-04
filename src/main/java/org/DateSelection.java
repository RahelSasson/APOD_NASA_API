package org;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.time.YearMonth;
import java.util.Scanner;

public class DateSelection extends JPanel {
    private String[] monthsArr = {"January", "February", "March", "April", "May", "June", "July", "August","September", "October", "November", "December"};
    private String[] daysArr;
    private String[] yearsArr;

    //private JPanel datePanel;
    private JComboBox months;
    private  JComboBox days;
    private  JComboBox years;
    int yearSelected1 = 1996;
    int monthSelected1 = 1;
    int daySelected1 = 1;
    public JButton picture;
    String URLString = " ";
    String titleString = " ";
    String explanationString = " ";


    public DateSelection(){
        super();
        super.setSize(500,100);

        buildDatePanel();
    }

    //NASA Astronomy Photo of the Day has been active every day since 1996
    //function sets all the years from 1996 until the current year
    //these years will be used to specify what year photo we want to extract
    //years will be displayed in a drop-down selection box on the GUI
    public void setYears () {
        int startYear = 1996;
        int nowYear = Year.now().getValue();
        this.yearsArr = new String[nowYear];
        int i = 0;
        while(startYear <= nowYear){
            yearsArr[i] = String.valueOf(startYear);
            i++;
            startYear++;
        }
    }

    //function sets specific number of days for specific month within a selected year
    //accounting for leap years
    //function accepts an initial year and month as parameters
    public void setDays (int year,int month) {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        this.daysArr = new String[daysInMonth];
        for(int i = 0; i < daysArr.length; i++) {
            daysArr[i] = String.valueOf((i+1));
        }
    }

    //building of the date panel and adding Item listeners for each selection
    public void buildDatePanel() {
        setYears(); // set the years array up until the current year

        this.months = new JComboBox(monthsArr); //fill JComboBox with desired months
        months.setBounds(100,50,100,50);
        months.setFont(new Font("Ink Free",Font.PLAIN,20));
        months.addItemListener(new DateSelection.MonthsItemsListener());

        this.years = new JComboBox(yearsArr); //fill JComboBox with desired years
        years.setBounds(200,50,100,50);
        years.setFont(new Font("Ink Free",Font.PLAIN,20));
        years.addItemListener(new DateSelection.YearsItemsListener());

        setDays(1996, 1);
        this.days = new JComboBox(daysArr); //fill JComboBox with desired days
        days.setBounds(300,50,100,50);
        days.setFont(new Font("Ink Free",Font.PLAIN,20));
        days.addItemListener(new DateSelection.DaysItemsListener());

        this.picture = new JButton("Get My Photo!"); //button to display photo
        picture.setBounds(400,50,100,50);
        picture.setFont(new Font("Ink Free",Font.PLAIN,20));
        //picture.doClick(); //do this at least once instantaneously with provided parameters
        picture.addActionListener(new DateSelection.ButtonListener());

        //add all components to the DateSelection panel
        super.add(months);
        super.add(days);
        super.add(years);
        super.add(picture);
    }

    //Item listener detecting changes in the year selected by the user
    //depending on the month and year selected; a different number of days will generate
    //this is to accommodate the varying days within each month with attention to leap years
    private class YearsItemsListener implements ItemListener {
        public void itemStateChanged(ItemEvent ie) {
            String yearSelected = (String) years.getSelectedItem();
            yearSelected1 = Integer.parseInt(yearSelected);
            days.removeAllItems();
            setDays(yearSelected1,monthSelected1);
            for(int i = 0; i < daysArr.length; i++)
                days.addItem(daysArr[i]);

        }
    }

    //Item Listener for detecting changes in the month selected by the user
    //depending on the month and year selected, a different number of days will generate
    //this is to accommodate the varying days within each month with attention to leap years
    private class MonthsItemsListener implements ItemListener {
        public void itemStateChanged(ItemEvent ie) {
            int monthSelected = months.getSelectedIndex() + 1;
            monthSelected1 = monthSelected;
            days.removeAllItems();
            setDays(yearSelected1,monthSelected1);
            for(int i = 0; i < daysArr.length; i++)
                days.addItem(daysArr[i]);

        }
    }

    //Item Listener for detecting changes in the day selected by the user
    private class DaysItemsListener implements ItemListener {
        public void itemStateChanged(ItemEvent ie) {
            int daySelected = days.getSelectedIndex() + 1;
            daySelected1 = daySelected;
        }
    }


    //Action Listener to detect if the 'picture' button was clicked
    //If the button is clicked; then a date was selected by the user
    //the date is then formatted into a String that matches the format of the date received by the NASA API call
    //that string is passed into function called 'operations' where the API call occurs
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == picture) {
                String formattedDay = String.format("%02d", daySelected1);
                String formattedMonth = String.format("%02d", monthSelected1);
                String formattedYear = String.valueOf(yearSelected1);
                String dateString = formattedYear + "-" + formattedMonth + "-" + formattedDay;
                try {
                    operations(dateString);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
    }

    //Function sends GET request to the NASA API;
    //and converts the response from the request to a plain object
    //the object can than be manipulated and applied to the GUI
    public void operations(String str) throws IOException {
        //URL of our GET request is passed into a URL object
        URL urlObl = new URL("https://api.nasa.gov/planetary/apod?api_key=Fck2kA42uvB8XevHKxHWJeRYqhrXIjDMsEvEWmcd&date="+str);
        //Establish connection to the API
        HttpsURLConnection connection = (HttpsURLConnection) urlObl.openConnection();
        connection.setRequestMethod("GET"); //GET request

        //Validating that the request has been sent successfully
        int reponseCode = connection.getResponseCode();
        if(reponseCode == HttpsURLConnection.HTTP_OK) {
            StringBuilder sb = new StringBuilder();
            Scanner s = new Scanner(connection.getInputStream());
            while(s.hasNext()) {
                sb.append(s.nextLine()); //storing our response in a StringBuilder object
            }
            ObjectMapper mapper = new ObjectMapper(); //using ObjectMapper to convert response string to a java object
            //creation of object 'javaObj'
            //within the APODStringExtraction class we specify what we want to extract from the response string
            APODStringExtraction javaObj = mapper.readValue(String.valueOf(sb),APODStringExtraction.class);
            setURLString(javaObj.getUrl());
            setExplanationString(javaObj.getExplanation());
            setTitleString(javaObj.getTitle());
        }
        else {
            System.out.println("error sending request");
        }
    }

    public void setExplanationString(String explanationString) {
        this.explanationString = explanationString;
    }
    public String getExplanationString() {
        return explanationString;
    }
    public void setURLString(String returnString) {
        this.URLString = returnString;
    }
    public String getURLString() {
        return URLString;
    }
    public void setTitleString(String titleString) {this.titleString = titleString; }
    public String getTitleString() { return titleString; }
    public JButton getPicture() {
        return picture;
    }
}

