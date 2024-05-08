/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventorymanagementsystem;

import com.inventorymanagementsystem.utils.ChatbotHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author rahul
 */
public class Chatbot extends Application {
    private JTextField textEnter;
    private JTextArea textchat;
    private TextField t1;
    private TextArea t2;

    private Button button;
    private Label label;
     private MediaPlayer mediaPlayer;
     private TextFlow f;
    private Stage stage2;
    private Stage stage3;
    private Stage stage4;
    private Scene change;
    private Scene scene;
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        stage2=primaryStage;
        stage3=primaryStage;
         textEnter=new JTextField();
         textchat=new JTextArea();
         t1=new TextField();
        t2=new TextArea();
        //Label work
        /*label=new Label();
        label.setText("ChatBot");
        label.setLayoutY(5);
        label.setTranslateY(-300);
        label.setId("a");*/
        Text t = new Text();
t.setX(10.0f);
t.setY(50.0f);
t.setCache(true);
t.setText("ChatBot");
t.setFill(javafx.scene.paint.Color.DARKMAGENTA); 
t.setFont(Font.font(null, FontWeight.BOLD,35));

Reflection r = new Reflection();
r.setFraction(0.7);
t.setEffect(r);
 t.setLayoutY(5);
 t.setTranslateY(-310);

        
        
        
       f=new TextFlow(t2);
        t1.setMaxSize(500, 30);
        t2.setMaxSize(500,510);
        t2.setId("x");
        t2.setPrefColumnCount(5);
        t2.setWrapText(true);
        t2.setPrefRowCount(5);
        //HBox box1=new HBox();
        //box1.getChildren().add(t2);
        //box1.setAlignment(Pos.TOP_CENTER);
        //box1.setLayoutX(100);
        
       //t1.setAlignment(Pos.BOTTOM_CENTER);
       t2.setLayoutX(50);
       t2.setTranslateX(2.5);
       t1.setLayoutX(50);
       t1.setTranslateX(2);
       t1.setLayoutY(8);
       t1.setTranslateY(270);
       //t1.alignmentProperty(Pos.BOTTOM_CENTER);
        //box1.setLayoutY(100);
        
        
        
       /* HBox box=new HBox();
        box.getChildren().addt1);
        box.setAlignment(Pos.BOTTOM_CENTER);*/
       // t2.setStyle("-fx-background-color:lightgreen;");
       // t1.setStyle("-fx-background-color:lightpink;-fx-text-fill:green;");
       InnerShadow g=new InnerShadow();
           g.setRadius(2);
           g.setWidth(5);
           g.setColor(javafx.scene.paint.Color.BLUEVIOLET);
           t2.setEffect(g);
           
           InnerShadow g1=new InnerShadow();
           g1.setRadius(2);
           g1.setWidth(5);
           g1.setColor(javafx.scene.paint.Color.DARKMAGENTA);
           t1.setEffect(g1);

        ProcessBuilder builder;
        Process process;
        String[] command = {"wsl", "bash", "-c", "ollama run llama3"};
        builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        try {
            process=builder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
           
        t2.setEditable(false);
        t1.setEditable(true);
        t1.setId("y");
        button=new Button("Change Theme");
       
        button.setLayoutY(8);
        button.setTranslateY(300);
        button.setLayoutX(8);
        button.setTranslateX(-50);
        t1.setPromptText("Type here"); 
          t1.setOnAction((ActionEvent e)->{
              String userInput = t1.getText();
              t2.appendText("\nYou: " + userInput + "\n\n"); // Append user input with newline

              // Pass the user input to the ChatbotHandler task
              ChatbotHandler chatbotHandler = new ChatbotHandler(userInput, process, builder);

              // Bind task's message property to update UI progressively (optional)
              chatbotHandler.messageProperty().addListener((obs, oldMessage, newMessage) -> {
                  System.out.println(newMessage);
                  Platform.runLater(() -> botSay(newMessage)); // Update UI with chatbot response
              });

              // When task is completed, you can perform any post-processing here
              chatbotHandler.setOnSucceeded(event -> {
                  // You may perform any post-processing here if needed
              });

              // Handle task failure
              chatbotHandler.setOnFailed(event -> {
                  // Handle task failure here if needed
              });

              // Start the task in a new thread
              new Thread(chatbotHandler).start();

              t1.setText("");
                   
                   
              
              
          });
          
        
        StackPane root = new StackPane();
        
         
         t2.setStyle("-fx-text-fill:green;");
        
        scene = new Scene(root,600,700);
        //scene.getStylesheets().add(Chatbot.class.getResource("Style.css").toExternalForm());
        Button b2=new Button("Default Theme");
         b2.setOnAction(e->
         {
              t.setFill(javafx.scene.paint.Color.DARKMAGENTA);
             root.setId("z1");
             t2.setStyle("-fx-text-fill:green;");
         });
         b2.setLayoutY(8);
         b2.setTranslateY(300);
         b2.setLayoutX(10);
         b2.setTranslateX(100);
         button.setOnAction(e->{
            //label.setStyle("-fx-text-fill:violet");
            t.setFill(javafx.scene.paint.Color.DEEPSKYBLUE);
            root.setId("z");
            t2.setStyle("-fx-text-fill:red;");
            
        });
         root.getChildren().addAll(t1,t2,button,t,b2);
        
        primaryStage.setTitle("Java AI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    Integer botcounter=0;
    public void botSay(String message) {
        if (botcounter==0)
        t2.appendText("\nBot: " + message); // Append the message without adding a newline at the end
    else
            t2.appendText("\n" + message); // Append the message without adding a newline at the end
        botcounter++;

    }
    public void askque()
    {
        Alert a=new Alert(AlertType.CONFIRMATION);
        a.setHeaderText("Jarvis is hearing....");
        a.setContentText("Tell me Your answer");
       Optional<ButtonType> result=a.showAndWait();
       if(result.get()==ButtonType.OK)

       {
           TextInputDialog dialog = new TextInputDialog("Your goodname");
                dialog.setTitle("Jarvis is hearing");
                dialog.setHeaderText("Yes,im hearing");
                dialog.setContentText("Tell me Your name:");

// Traditional way to get the response value.
                Optional<String> result1 = dialog.showAndWait();
                if (result1.isPresent()){
                    if(result1.toString().contains("chintan"))
                    {
                        botSay("'"+result1.get()+" Chodu "+"'"+"Nice name..lol!");
                    }
                    else{
                    botSay("'"+result1.get()+"'"+" oh its a really nice name!");
                    }
                }
       }
       else{
           botSay("oops! It looks like u don't trust me..!");
       }
    }
    public void astat()
    {
        t2.appendText("\n ***** Welcome in Statistical mode *****\n");
        Alert a=new Alert(AlertType.INFORMATION);
        a.setTitle("Arithmetic mean");
        a.setHeaderText("Enter the no of element..jarvis is hearing..");
        Optional<ButtonType> result=a.showAndWait();
        if(result.get()==ButtonType.OK)
        {
            TextInputDialog dialog=new TextInputDialog();
            dialog.setContentText("Enter the no of elements..");
            dialog.setHeaderText("counter for numbers");
            Optional<String> result1=dialog.showAndWait();
            int val;
            val=Integer.parseInt(result1.get().toString());
            botSay("You have entered "+result1.get()+"values for oepration!");
            
            double []x=new double[val];
            double val1;
            double val2;
            double fsum = 0;
            double fidisum =0;
            double []x1=new double[val];
            for(int i=0;i<val;i++)
            {
                
                        TextInputDialog dialog1=new TextInputDialog();
                        dialog1.setContentText("Enter the starting point for class(x)");
                        Optional<String> result2=dialog1.showAndWait();
                        int start=Integer.parseInt(result2.get().toString());
                        x[i]+=start;
                        
                             TextInputDialog dialog2=new TextInputDialog();
                        dialog2.setContentText("Enter the ending point for class(x)");
                        Optional<String> result3=dialog2.showAndWait();
                        int end=Integer.parseInt(result3.get().toString());
                        x1[i]+=end;
                            
                        
            }
                        double[] fi=new double[val];
                        
                        for(int i=0;i<val;i++)
                        {
                            TextInputDialog dialog3=new TextInputDialog();
                            dialog3.setHeaderText("values for Fi!");
                            dialog3.setContentText("Enter values for Fi");
                            Optional<String> result4=dialog3.showAndWait();
                            int fval=Integer.parseInt(result4.get().toString());
                            
                            fi[i]+=fval;
                            fsum+=fi[i];
                            
                        } 
                        double[] xfinal=new double[val];
                        Random r=new Random();
                        double rand = 0;
                        for(int i=0;i<val;i++)
                        {
                            xfinal[i]=(x[i]+x1[i])/2;
                            
                        rand=xfinal[r.nextInt(xfinal.length/2)];
                        
                        }
                        double h=0;
                        
                        
                        double[] di=new double[val];
                        for(int i=0;i<val;i++)
                        {
                            h=x1[0]-x[0];
                            di[i]=(xfinal[i]-rand)/h;
                        }
                        double[] fidi=new double[val];
                        for(int i=0;i<val;i++)
                        {
                            fidi[i]=fi[i]*di[i];
                            fidisum+=fidi[i];
                            
                        }
                        t2.appendText("\n+---------------------------------------------------+\n");
                        t2.appendText("Class\t\txi\t\tfi\t\tdi\t\tfidi");
                        t2.appendText("\n+---------------------------------------------------+\n");
                        for(int i=0;i<val;i++)
                        {
                            t2.appendText(x[i]+"-"+x1[i]+"\t\t"+xfinal[i]+"\t\t"+fi[i]+"\t\t"+di[i]+"\t\t"+fidi[i]+"\n");
                            t2.appendText("+---------------------------------------------------+\n");
                           
                          
                            
                        }
                         t2.appendText("\t\t\t\t\t"+fsum+"\t\t\t\t"+fidisum);
                         t2.appendText("\n\n A -> "+rand +", H -> "+h);
                         t2.appendText ("\n\n A+fidi/fi*h ->"+(rand+(fidisum/fsum))*h);
                        
                        
                
            }
        }
        
    
     public void stat()
    {
       t2.appendText("\n ***** Welcome in Statistical mode *****\n");
        Alert a=new Alert(AlertType.INFORMATION);
        a.setTitle("Simple mean");
         a.setHeaderText("Enter your values.. jarvis is hearng..");

       Optional<ButtonType> result=a.showAndWait(); 
       if(result.get()==ButtonType.OK)
       {
                TextInputDialog dialog = new TextInputDialog("Enter the no of data u want");
                    dialog.setContentText("Enter your value");
                    Optional<String> result1=dialog.showAndWait();
                    result1.get();
                    int val;
                    val=Integer.parseInt(result1.get().toString());
                    botSay(" You have entered:"+result1.get()+" values for sum");
                    float [] arr=new float[val];
                    for(int i=0;i<val;i++)
                    {
                        TextInputDialog dialog1=new TextInputDialog();
                        dialog1.setContentText("Enter numbers");
                        Optional<String> result2=dialog1.showAndWait();
                        int val1=Integer.parseInt(result2.get().toString());
                        arr[i]+=val1;    
                    }
                   
                    /*for(int i=0;i<val;i++)
                    {
                        t2.appendText(arr[i]+"\n");
                    }*/
                    float [] fi=new float[val];
                    float sum=0;
                    for(int i=0;i<val;i++)
                    {
                        TextInputDialog dialog2=new TextInputDialog();
                        dialog2.setContentText("Enter values for Fi");
                        Optional<String> result3=dialog2.showAndWait();
                        int val2=Integer.parseInt(result3.get().toString());
                        fi[i]+=val2; 
                        sum+=fi[i];
                        
                    }
                    float [] fixi=new float[val];
                    
                    float sum1=0;
                    for(int i=0;i<val;i++)
                    {
                        fixi[i]=arr[i]*fi[i];
                        sum1+=fixi[i];
                    }
                    
                    t2.appendText("\n+------------------------------------------+\n");
                    t2.appendText("\nxi\t\tfi\t\tfixi\n");
                    t2.appendText("\n+------------------------------------------+\n");
                    for(int i=0;i<val;i++)
                    {
                        t2.appendText(arr[i]+"\t\t"+fi[i]+"\t\t"+fixi[i]);
                         t2.appendText("\n+------------------------------------------+\n");
                        
                        
                    }
                    t2.appendText("\t\t"+sum+"\t\t"+sum1);
                    t2.appendText("\n+------------------------------------------+\n");
                    t2.appendText("\n Your Final Answer is as follow!\n");
                    t2.appendText("mean= fixi/fi\t=");
                    double ans;
                    ans=sum1/sum;
                    t2.appendText(""+ans);
                   
                    
       }
       
       
       
        
        
    }
    public void get()
    {
        Scanner sc=new Scanner(System.in);
        String s;
        s=sc.nextLine();
        botSay("Enter something");
        t1.setText(s);
        if(t1.toString().contains("a"))
        {
            botSay("u entered");
        }
         
        
        
    }
   

    /**
     *
     */
//    public void getWeather() throws IOException ,MalformedURLException//throws IOException, MalformedURLException, JSONException 
//    {
//        /*Image i=new Image("http://openweathermap.org/img/w/10d.png");
//        BackgroundImage c1 = new BackgroundImage(i, null, null, null, null);
//        Background g=new Background(c1);*/
//       
//      
//        
//        
//        OpenWeatherMap o=new OpenWeatherMap("d83e0eb2cf74695543bbfec2d94722d5");
//        CurrentWeather c=o.currentWeatherByCityCode(1279233);
//        o.setUnits(OpenWeatherMap.Units.METRIC);
//        botSay("Here is the Details\n");
//        t2.appendText("City: "+c.getCityName());
//        t2.appendText("\n");
//       t2.appendText("Temperature: " + c.getMainInstance().getMaxTemperature()
//                            + "/" + c.getMainInstance().getMinTemperature() + "\'F");
//        t2.appendText("\n");
//        t2.appendText("Clouds: "+c.getCloudsInstance().getPercentageOfClouds());
//        t2.appendText("\n");
//        t2.appendText("Wind Speed : "+c.getWindInstance().getWindSpeed()+" km");
//        /*if(c.getRainInstance().hasRain()==true)
//        {
//            t2.appendText("Rain stauts :Its raining" );
//        }
//        else{
//            t2.appendText("Rain status:Its not Raining");
//        }*/
//        t2.appendText("\n");
//     t2.appendText("rain status : "+c.getWeatherInstance(0).getWeatherDescription());
//     t2.appendText("\n");
//     t2.appendText("Sunset Time : "+c.getSysInstance().getSunsetTime());
//     
//    
//     
//    
//     
//    }
   /* public void google()
    {
        Browser browser=new Browser();
        BrowserView view=new BrowserView(browser);
        view.setMaxSize(750, 500);
        view.setLayoutY(5);
        view.setTranslateY(-35);
         Button b1=new Button("Go back to chatbot");
           b1.setOnAction(e->{
               stage3.setScene(scene);
               botSay("Task is finished sir! I hope i did it well!");
           }
           );
           
           b1.setLayoutY(5);
           b1.setTranslateY(-300);
       
        StackPane s=new StackPane();
        s.getChildren().addAll(view,b1);
        Scene scene1 = new Scene(s, 700, 500);
        stage3.setScene(scene1);
         browser.loadURL("https://www.google.co.in");
    }
    public void map()
    {
        
        

        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);
        //BorderPane stackp=new BorderPane();
        //stack.getChildren().add(view);
       
        view.setMaxSize(750,500);
        view.setLayoutY(5);
        view.setTranslateY(-35);
        Button b1=new Button("Go back to chatbot");
           b1.setOnAction(e->{
               stage2.setScene(scene);
           }
           );
           
           b1.setLayoutY(5);
           b1.setTranslateY(-300);
           Button b2=new Button("Expand Map");
           b2.setOnAction(e->
           {
               view.setMaxSize(700,700);
               view.setLayoutY(5);
               view.setTranslateY(50);
               b1.setLayoutY(5);
               b1.setTranslateY(-315);
               b2.setLayoutY(5);
               b2.setTranslateY(-315);
           }
           );
           b2.setLayoutY(5);
           b2.setTranslateY(-300);
           b2.setLayoutX(5);
           b2.setTranslateX(150);
         StackPane stack=new StackPane();
       stack.getChildren().addAll(view,b1,b2);
       
       stack.setMaxSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
        
          Shadow g=new Shadow();
           g.setRadius(2);
           view.setEffect(g);
 stack.setId("bg");
Scene scene1 = new Scene(stack, 700, 500);
scene1.getStylesheets().add(Chatbot.class.getResource("Style.css").toExternalForm());
 stage2.setScene(scene1); 
browser.loadURL("http://maps.google.com");

/*FadeTransition ft=new FadeTransition();
ft.setDuration(javafx.util.Duration.millis(3000));
ft.setNode(view);
ft.setFromValue(1.0);
     ft.setToValue(0.3);
     ft.setCycleCount(4);
     ft.setAutoReverse(true);
 
     ft.play();

        
    } */
    }
           
       
    

    /**
     * @param args the command line arguments
     */
    
    

