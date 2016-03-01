
import java.sql.*;
import javax.swing.JFrame;

 /******************************************************************************
 * File:NewJFrame.java
 * Course: 17655
 * Project: Assignment 2
 * Copyright: Copyright (c) 2009 Carnegie Mellon University
 * Versions:
 *	1.0 November 2009 - Initial rewrite of original assignment 2 (ajl).
 *
 * This class defines a GUI application that allows EEP shipping personnel to
 * mark orders as shipped.
 *
 ******************************************************************************/
/*
 * Created on Feb 4, 2010, 7:40:03 PM
 *
 * @author lattanze
 */
public class ShippingNewJFrame extends JFrame {
    String updateOrderID;
    String versionID = "v2.10.10";
    private AuthManagerInterface securityImpl = new AuthManager();
    // Token needs to be set dynamically after the login has been completed
    private String token ;
    
    /** Creates new form NewJFrame */
    private JFrame mainform;
    
     public ShippingNewJFrame(JFrame mainform) {
        this.mainform = mainform;
        initComponents();
        jLabel1.setText("Inventory Management Application " + versionID);
    }
    public ShippingNewJFrame() {
        initComponents();
        jLabel1.setText("Shipping Application " + versionID);
    }
    
      public void setToken(String token) {
        this.token = token;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        Logout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Shipping Application");

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField2.setEditable(false);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Order Information:");

        jLabel5.setText("Customer First Name:");

        jLabel6.setText("Last Name:");

        jTextField3.setEditable(false);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel7.setText("Phone Number:");

        jTextField4.setEditable(false);
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel8.setText("Mailing Address");

        jTextArea2.setColumns(20);
        jTextArea2.setEditable(false);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton1.setText("Mark As Shipped");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Show  Pending Orders");
        jButton2.setDefaultCapable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Select Order");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel10.setText("Order Number : Order Date & Time: Customer Name");

        jTextArea3.setColumns(20);
        jTextArea3.setEditable(false);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        jLabel9.setText("Order Items ");

        jLabel11.setText("Messages");

        jButton4.setText("Show Shipped Orders");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextArea4.setColumns(20);
        jTextArea4.setEditable(false);
        jTextArea4.setRows(5);
        jScrollPane4.setViewportView(jTextArea4);

        jLabel2.setText("Order Date:");

        jTextField5.setEditable(false);
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        Logout.setText("Logout");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jTextField5)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(609, 609, 609)
                                .addComponent(Logout)))
                        .addGap(45, 45, 45))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)
                            .addComponent(jLabel4)
                            .addComponent(jLabel10))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(431, 431, 431))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(Logout))
                .addGap(8, 8, 8)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel11))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // jButton2 is responsible for refreshing the list of pending
        // orders.
        getPendingOrders();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // This button gets the selected line of text from the
        // order list window jTextArea1. The line of text is parsed for the
        // order number. Once the order number is parsed, then the order is
        // retrieved from the orders database. The ordertabel field from the
        // record contains the name of the table that has the items that make
        // up the order. This table is opened and all the items are listed
        // in jTextArea3.

        Boolean connectError = false;       // Error flag
        String errString = null;            // String for displaying errors
        int beginIndex;                     // Parsing index
        int endIndex;                       // Parsing index
        String msgString = null;            // String for displaying non-error messages
        String orderSelection = null;       // Order selected from TextArea1
        String orderTable = null;           // The name of the table containing the order items
        String orderID = null;              // Product ID pnemonic
        String productDescription = null;   // Product description
        ResultSet res = null;               // SQL query result set pointer
        Statement s = null;                 // SQL statement pointer
        Boolean orderBlank = false;         // False: order string is not blank
        String SQLStatement;                // SQL query

        // this is the selected line of text
        orderSelection =  jTextArea1.getSelectedText();

        // make sure its not blank
        if (orderSelection.length() > 0 )
        {
            // get the product ID
            beginIndex = 0;
            beginIndex = orderSelection.indexOf(" # ", beginIndex);
            beginIndex = beginIndex + 3; //skip past _#_
            endIndex = orderSelection.indexOf(" :", beginIndex);
            orderID = orderSelection.substring(beginIndex,endIndex);

        } else {

            msgString = ">> Order string is blank...";
            jTextArea4.setText("\n"+msgString);
            orderBlank = true;

        } // Blank string check

        // If an order was selected, then connect to the orderinfo database. In
        // all normal situations this would be impossible to do since the select
        // button is disabled until an order is selected... just in case the
        // check is here.

        // Code removed for database connectivity

        if ( !connectError && !orderBlank )
        {
            try
            {
//                s = DBConn.createStatement();
//                SQLStatement = "SELECT * FROM orders WHERE order_id = " + Integer.parseInt(orderID);
                  // Calling the authorization layer which will help to fetch data using data access layer
                  res = securityImpl.selectOrder("orders",orderID,this.token);
                
                // Get the information from the database. Display the
                // first and last name, address, phone number, address, and
                // order date. Same the ordertable name - this is the name of
                // the table that is created when an order is submitted that
                // contains the list of order items.

//               while (res.next()) {
//                   System.out.println("res ob "+ res.getObject(1));
//               }
//               res = securityImpl.selectOrder("orders",orderID,this.token);
                while (res.next()) {
                  orderTable = res.getString(9);         // name of table with list of items
                  jTextField2.setText(res.getString(3)); // first name
                  jTextField3.setText(res.getString(4)); // last name
                  jTextField4.setText(res.getString(6)); // phone
                  jTextField5.setText(res.getString(2)); // order date
                  jTextArea2.setText(res.getString(5));  // address

                } // for each element in the return SQL query

                // get the order items from the related order table
                //SQLStatement = "SELECT * FROM " + orderTable;
                // Calling the authorization layer which will help to fetch data using data access layer
                res = securityImpl.select(orderTable,this.token);
                // list the items on the form that comprise the order
                jTextArea3.setText("");
                while (res.next())
                {
                    msgString = res.getString(1) + ":  PRODUCT ID: " + res.getString(2) +
                         "  DESCRIPTION: "+ res.getString(3) + "  PRICE $" + res.getString(4);
                    jTextArea3.append(msgString + "\n");

                } // while

                // This global variable is used to update the record as shipped
                updateOrderID = orderID;

                // Update the form
                jButton1.setEnabled(true);
                msgString = "RECORD RETRIEVED...";
                jTextArea4.setText(msgString);
                
            } catch (Exception e) {

                errString =  "\nProblem getting order items:: " + e;
                jTextArea1.append(errString);

            } // end try-catch

        } // connect and blank order check
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // This method is responsible changing the status of the order
        // to shipped.

        Boolean connectError = false;       // Error flag
        String errString = null;            // String for displaying errors
        String msgString = null;            // String for displaying non-error messages
        ResultSet res = null;               // SQL query result set pointer
        int rows;                           // Rows updated
        Statement s = null;                 // SQL statement pointer
        String SQLStatement = null;         // SQL statement string

        // Connect to the order database - Code Removed
        
        // If we are connected, then we update the shipped status

        if ( !connectError )
        {
            try
            {
                // first we create the query
                //s = DBConn.createStatement();
                //SQLStatement = "UPDATE orders SET shipped=" + true + " WHERE order_id=" + updateOrderID;

                // execute the statement
                rows = securityImpl.setOrderShipped(updateOrderID, true, this.token);

                // if the query worked, then we display the data in TextArea 4 - BTW, its highly
                // unlikely that the row won't exist and if it does the database tables are
                // really screwed up... this should not fail if you get here, but the check (in the
                // form of the else clause) is in place anyway

                if (rows > 0)
                {
                   jTextArea4.setText("\nOrder #" + updateOrderID + " status has been changed to shipped.");

                } else {

                   jTextArea4.setText("\nOrder #" + updateOrderID + " record not found.");

                } // execute check

                // Clean up the form
                jButton1.setEnabled(false);
                jButton3.setEnabled(false);
                jTextArea1.setText("");
                jTextArea2.setText("");
                jTextArea3.setText("");
                jTextField2.setText("");
                jTextField3.setText("");
                jTextField4.setText("");
                jTextField5.setText("");

            } catch (Exception e) {

                errString =  "\nProblem updating status:: " + e;
                jTextArea4.append(errString);
                jTextArea1.setText("");

            } // end try-catch

        } // if connect check

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // This button will display the list of orders that have already
        // have been shipped.

        getShippedOrders();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        this.setVisible(false);
        this.mainform.setVisible(true);
        securityImpl.logout(token);
       
    }//GEN-LAST:event_LogoutActionPerformed

    private void getPendingOrders() {

        // This method is responsible for querying the orders database and
        // getting the list of pending orders. This are orders that have not
        // been shipped as of yet. The list of pending orders is written to
        // jTextArea1.

        Boolean connectError = false;       // Error flag
        String errString = null;            // String for displaying errors
        String msgString = null;            // String for displaying non-error messages
        ResultSet res = null;               // SQL query result set pointer
        Statement s = null;                 // SQL statement pointer
        int shippedStatus;                  // if 0, order not shipped, if 1 order shipped

        // Clean up the form before we start

        jTextArea1.setText("");
        jTextArea2.setText("");
        jTextArea3.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");

        // Connect to the order database
        // Remove data for data base connectivity

        // If we are connected, then we get the list of trees from the
        // inventory database

        if ( !connectError )
        {
            try
            {
                // Create a query to get all the orders and execute the query
                //s = DBConn.createStatement();
                //res = s.executeQuery( "Select * from orders" );
                res = securityImpl.select("orders", this.token);

                //Display the data in the textarea
                jTextArea1.setText("");

                // For each row returned, we check the shipped status. If it is
                // equal to 0 it means it has not been shipped as of yet, so we
                // display it in TextArea 1. Note that we use an integer because
                // MySQL stores booleans and a TinyInt(1), which we interpret
                // here on the application side as an integer. It works, it just
                // isn't very elegant.
                while (res.next())
                {
                    shippedStatus = Integer.parseInt(res.getString(8));

                    if ( shippedStatus == 0 )
                    {
                        msgString = "ORDER # " + res.getString(1) + " : " + res.getString(2) +
                              " : "+ res.getString(3) + " : " + res.getString(4);
                        jTextArea1.append(msgString+"\n");

                    } // shipped status check

                } // while

                // notify the user all went well and enable the select order
                // button
                jButton3.setEnabled(true);
                msgString =  "\nPENDING ORDERS RETRIEVED...";
                jTextArea4.setText(msgString);

            } catch (Exception e) {

                errString =  "\nProblem getting tree inventory:: " + e;
                jTextArea4.append(errString);

            } // end try-catch
            
        } // if connect check

    } // getPendingOrders

    private void getShippedOrders() {

        // This method is responsible for querying the orders database and
        // getting the list of orders that have been shipped. The list of shipped
        // orders is written to jTextArea1.

        Boolean connectError = false;       // Error flag
        String errString = null;            // String for displaying errors
        String msgString = null;            // String for displaying non-error messages
        ResultSet res = null;               // SQL query result set pointer
        Statement s = null;                 // SQL statement pointer
        int shippedStatus;                  // if 0, order not shipped, if 1 order shipped

        // Clean up the form before we start

        jTextArea1.setText("");
        jTextArea2.setText("");
        jTextArea3.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");

        // Connect to the order database
        // Remove database connectivity code - Moved to Data access layer

        // If we are connected, then we get the list of trees from the
        // inventory database

        if ( !connectError )
        {
            try
            {
                // Create a query to get all the rows from the orders database
                // and execute the query.
                //s = DBConn.createStatement();
                //res = s.executeQuery( "Select * from orders" );
                res = securityImpl.select("orders", this.token);
                //Display the data in the textarea
                jTextArea1.setText("");

                // For each row returned, we check the shipped status. If it is
                // equal to 0 it means it has not been shipped as of yet, so we
                // display it in TextArea 1. Note that we use an integer because
                // MySQL stores booleans and a TinyInt(1), which we interpret
                // here on the application side as an integer. It works, it just
                // isn't very elegant.

                while (res.next())
                {
                    shippedStatus = Integer.parseInt(res.getString(8));

                    if ( shippedStatus == 1 )
                    {
                        msgString = "SHIPPED ORDER # " + res.getString(1) + " : " + res.getString(2) +
                              " : "+ res.getString(3) + " : " + res.getString(4);
                        jTextArea1.append(msgString+"\n");

                    } // shipped status check

                } // while

                jButton1.setEnabled(false);
                jButton3.setEnabled(false);

                msgString =  "\nSHIPPED ORDERS RETRIEVED...";
                jTextArea4.setText(msgString);

            } catch (Exception e) {

                errString =  "\nProblem getting tree inventory:: " + e;
                jTextArea4.append(errString);

            } // end try-catch

        } // connect check

    } // getPendingOrders


    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShippingNewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Logout;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables

}
