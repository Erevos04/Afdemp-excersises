/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaproject1;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Orestis
 */
class Menus {
    private static final org.apache.logging.log4j.Logger log =  LogManager.getLogger(Menus.class.getName());
    private String ConnectedUser;

protected void ShowMenus(String ConnectedUser){
    this.ConnectedUser=ConnectedUser;
    
    if (ConnectedUser.contains("user")){
        ClearScreen();       
        byte Menuchoice=0;
        
        do {
            System.out.println("Welcome "+ConnectedUser+".\nPlease select one action :");
            System.out.println("\n[0] - Exit the application.\n[1] - View your account balance."
                + "\n[2] - Deposit to Admin account.\n[3] - Deposit to User account."
                + "\n[4] - Send statement to file.");
            Scanner sc = new Scanner(System.in);
            try {
                Menuchoice = sc.nextByte();
            }
                catch(Exception e){
                System.out.println("False menu input."); 
                return;
                }
            try {    
                log.info("User : '"+ConnectedUser+"' selected option "+Menuchoice+" in the menus.");
                switch (Menuchoice)
                {
                    case 1:
                        //View account
                        ClearScreen();
                        new InternalBankAccounts().printBalanceofSingleUser(ConnectedUser);
                        RefreshMenus();
                        break;
                    case 2:
                        //Deposit to admin
                        ClearScreen();
                        new Database().TransferBalancetoAdmin(ConnectedUser);
                        new InternalBankAccounts().printBalanceofSingleUser(ConnectedUser);
                        RefreshMenus();
                        break;
                    case 3:
                        //Deposit to user
                        ClearScreen();
                        new Database().TransferBalance(ConnectedUser);
                        new InternalBankAccounts().printBalanceofSingleUser(ConnectedUser);   
                        RefreshMenus();
                        break;
                    case 4:
                        //Send statement 
                        ClearScreen();
                        new Files().SendStatement(ConnectedUser);
                        RefreshMenus();
                        break;   
                }
            } 
            catch (SQLException ex) {
                System.out.println("Application encoutered an error. Please Contact IT.");
                log.error(ex.getLocalizedMessage());
                RefreshMenus();              
            }
        } 
        while (Menuchoice != 0);
            if (Menuchoice==0) {               
                ClearScreen();
                new Files().SendStatement(ConnectedUser);
                System.out.println("Exiting application...\nThank you!");
                System.exit(0);
            }
    }
    else {
        ClearScreen();       
        byte Menuchoice=0;
        
            do {
                System.out.println("Welcome "+ConnectedUser+".\nPlease select one action :");
                System.out.println("\n[0] - Exit the application.\n[1] - View Admin account balance."
                + "\n[2] - View all Users account balance.\n[3] - Deposit to User account."
                + "\n[4] - Withdraw from User account.\n[5] - Send statement to file."
                + "\n[6] - Create a new User.");
                Scanner sc = new Scanner(System.in);
            try {
                Menuchoice = sc.nextByte();
            }
                catch(Exception e){
                System.out.println("False menu input."); 
                return;
                }
            try {            
                log.info("User : 'admin' selected option "+Menuchoice+" in the menus.");
                switch (Menuchoice)
                {
                    case 1:
                        //View admin account
                        ClearScreen();
                        new InternalBankAccounts().printBalanceofSingleUser(ConnectedUser);
                        RefreshMenus();
                        break;
                    case 2: 
                        //View users accounts
                        ClearScreen();
                    new InternalBankAccounts().printBalanceofAllUsers();
                        RefreshMenus();
                        break;
                    case 3: 
                        //Deposit to user
                        ClearScreen();
                        new Database().TransferBalance(ConnectedUser);
                        new InternalBankAccounts().printBalanceofSingleUser(ConnectedUser);
                        RefreshMenus();
                        break;
                    case 4:
                        //Withdraw from user
                        ClearScreen();
                        new Database().WithdrawBalance(ConnectedUser);
                        new InternalBankAccounts().printBalanceofSingleUser(ConnectedUser);
                        RefreshMenus();
                        break;
                    case 5:
                        //Send statement
                        ClearScreen();
                        new Files().SendStatement(ConnectedUser);
                        RefreshMenus();
                        break;
                    case 6:
                        //Create a new user
                        ClearScreen();
                        new Database().CreateNewUser(ConnectedUser);
                        RefreshMenus();
                        break;
                }   
            } 
            catch (SQLException ex) {
                System.out.println("Application Encoutered an error. Please Contact IT.");
                log.error(ex.getLocalizedMessage());
                RefreshMenus();
            }
        } 
        while (Menuchoice != 0);
            if (Menuchoice==0) {
                ClearScreen();
                new Files().SendStatement(ConnectedUser);
                System.out.println("Exiting application...\nThank you!");
                System.exit(0);
            }
           
    }
}

protected void RefreshMenus(){
    System.out.println("Press Enter key to continue...");
    
        try
        {           
            System.in.read();
            
        }  
        catch(Exception e)
        {} 
        ClearScreen();
        
       
    
}

protected void ClearScreen(){
   
    if (System.getProperty("os.name").contains("Windows")){
        try {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException ex) {

            }
        } catch (IOException ex) {

        }
    } 
    else
        try {
            Runtime.getRuntime().exec("clear");
        } catch (IOException ex) {

        }
    }
}
