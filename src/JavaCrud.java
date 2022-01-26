import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class JavaCrud {
    private JPanel Main;
    private JTextField textName;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton1;
    private JTextField textPrice;
    private JTextField textQty;
    private JButton searchButton;
    private JTextField textpid;

    public static void main(String[] args) {
        JFrame frame = new JFrame("JavaCrud");
        frame.setContentPane(new JavaCrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;
    public JavaCrud() {

        Connect();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name, price, qty;
                name = textName.getText();
                price = textPrice.getText();
                qty = textQty.getText();

                try{
                    pst = con.prepareStatement("insert into products(pname,price,qty) values(?,?,?)");
                    pst.setString(1,name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added Successfully!!!");
                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        deleteButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textpid.getText();
                try{
                    pst = con.prepareStatement("delete from products where pid = ?");
                    pst.setString(1,id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted Successfully!!!");
                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textpid.setText("");
                    textName.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String id = textpid.getText();

                    pst = con.prepareStatement("select pname,price, qty from products where pid = ?");
                    pst.setString(1,id);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()==true){
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);;

                        textName.setText(name);
                        textPrice.setText(price);
                        textQty.setText(qty);
                    }
                    else{
                        textName.setText("");
                        textPrice.setText("");
                        textQty.setText("");
                        JOptionPane.showMessageDialog(null, "There is no Recorded Product");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid, name, price, qty;
                pid = textpid.getText();
                name = textName.getText();
                price = textPrice.getText();
                qty = textQty.getText();

                try {
                    pst = con.prepareStatement("update products set pname = ?, price = ?,qty = ? where pid = ?");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.setString(4, pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated Successfully!!!");

                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();
                    textpid.setText("");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }


            }
        });
    }



    public void Connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/gbproducts","root","root");
            System.out.println("Success");
        }
        catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
