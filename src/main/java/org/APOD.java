package org;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


import static java.lang.System.console;

public class APOD extends JFrame {

    private DateSelection dateSelectionPanel;
    private JPanel panel;
    private String str;

    private JLabel title;
    private JLabel imageLabel;
    private JLabel explanation;
    private String explanationStr;
    private String titleStr;
    JScrollPane scroll;

    public APOD() throws IOException {
        super();
        super.setTitle("Astronomy Photo of the Day");
        super.setSize(1200,600);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLayout(new BorderLayout());

        buildOGPanel(); //building original Panel (DatSelection.java)
        buildPanel(); //building the panel to display image amd description

        dateSelectionPanel.getPicture().addMouseListener(getMouseListener());

        //adding a scroll bar to the image/description panel
        this.scroll = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

       super.add(dateSelectionPanel,BorderLayout.NORTH);
       super.add(scroll,BorderLayout.CENTER);

        super.setVisible(true);
    }

    public void buildOGPanel() {
        this.dateSelectionPanel = new DateSelection();
    }

    //Mouse Listener that listens for an event click on the DateSelection panel
    //once the mouse click on the 'picture' button in DateSelection.java has been released;
    //we retrieve all the desired information from DateSelection.java
    //such as the imageURL, description and title
    private MouseListener getMouseListener() {

        return new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                str = dateSelectionPanel.getURLString(); //retrieve image URL
                try {
                    actions(str); //call actions method
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                explanationStr = dateSelectionPanel.getExplanationString(); //retrieve explanation of image string
                String[] splitString = explanationStr.split("[.:;]");
                String newStr = "<html>Description:";
                for (int i = 0; i < splitString.length; i++) {
                    newStr = newStr + ("<br>" + splitString[i]+".");
                }
                newStr += "</html>";

                if(explanationStr!= "") {
                    explanation.setText(newStr);
                }
                else
                    explanation.setText("description unavailable for this image");

                titleStr = dateSelectionPanel.getTitleString(); //retrieve title of image string
                title.setText(titleStr);

                panel.repaint();
            }
        };
    }


    ////building the panel to display image, title amd description
    public void buildPanel() throws IOException {

        this.panel = new JPanel();
        panel.setLayout(new BorderLayout());

        this.title = new JLabel("");
        title.setBounds(300,8,300,60);
        //title.setPreferredSize(new Dimension(300,60));
        title.setFont(new Font("Ink Free",Font.PLAIN,50));

        this.explanation = new JLabel();
        explanation.setFont(new Font("Ink Free",Font.PLAIN,11));

        this.imageLabel  = new JLabel(" ");

        panel.add(title,BorderLayout.NORTH);
        panel.add(explanation,BorderLayout.CENTER);
        panel.add(imageLabel,BorderLayout.SOUTH);
    }

    //actions method used in Mouse Listener to read the image from the URL
    //and set the JLabel 'imageLabel' with it
    public void actions(String str) throws IOException {
        if(isUrlValid(str)) { //verify that the URL is valid

            URL urll = new URL(str);
            BufferedImage image = ImageIO.read(urll);
            ImageIcon imageIC = new ImageIcon(image);
            imageLabel.setIcon(imageIC);
        }
    }

    //URL verification method
    //returns a boolean
    public static boolean isUrlValid(String url) throws IOException {
        try {
            URL obj = new URL(url);
            obj.toURI();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        new APOD();
    }

}
