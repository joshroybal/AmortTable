import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.text.NumberFormat;

class DisplayTable extends Frame
   implements ActionListener, WindowListener
{  Label AmountPrompt;
   Label YearsPrompt;
   Label RatePrompt;
   TextField amount;
   TextField noyears;
   TextField rate;
   TextArea table;

   DisplayTable(String s)
   {  super(s);
      setSize(700,500);
      setLayout(new FlowLayout());
      addWindowListener(this);
      // Amount/Principal field
      AmountPrompt = new Label("A : $");
      add(AmountPrompt);
      amount = new TextField(10);
      add(amount);
      // No. of years field
      YearsPrompt = new Label("No. of years : ");
      add(YearsPrompt);
      noyears = new TextField(2);
      add(noyears);
      // interest rate field
      RatePrompt = new Label("I : ");
      add(RatePrompt);
      rate = new TextField(8);
      add(rate);

      rate.addActionListener(this);

      table = new TextArea(27,55);
      table.setFont(new Font("Monospaced", Font.PLAIN, 12 ));
      add(table);
      table.setEditable(false);
      setVisible(true);
   }

   public void windowClosed(WindowEvent event) {}
   public void windowDeiconified(WindowEvent event) {}
   public void windowIconified(WindowEvent event) {}
   public void windowActivated(WindowEvent event) {}
   public void windowDeactivated(WindowEvent event) {}
   public void windowOpened(WindowEvent event) {}
   public void windowClosing(WindowEvent event) { System.exit(0); }

   public void actionPerformed(ActionEvent event)
   {  table.setText("");
      makeTable();
   }

   public void makeTable()
   {  float a = Float.parseFloat(amount.getText());
      int n = 12 * Integer.parseInt(noyears.getText());
      float r = Float.parseFloat(rate.getText()) / 1200.F;

      float interest = 0.F;

      float f = (float) Math.pow((1.F + r), n);
      float pmt = (a * f * r) / (f - 1.F);

      float[][] at = new float[n][4];
      for (int i = 0; i < n; i++)
      {  at[i][0] = a;
         at[i][1] = r*at[i][0];
         at[i][2] = pmt - at[i][1];
         at[i][3] = at[i][0] - at[i][2];
         a = at[i][3];
         interest += at[i][1];
      }

      NumberFormat currency = NumberFormat.getCurrencyInstance();

      table.append(" monthly payment = " + currency.format(pmt) + "\n");
      for (int i = 0; i < n; i++)
      {  StringBuffer row = new StringBuffer();
         for (int j = 0; j < 4; j++)
         {  // String field = String.format("%11.2f  ", at[i][j]);
            String field = String.format("%13s", currency.format(at[i][j]));
            row.append(field);
         }
         table.append(row.toString() + "\n");
      }
      table.append(" total interest paid = " + currency.format(interest)
            + "\n");
   }
}

class AmortTable
{  public static void main(String[] args)
   {  new DisplayTable("Amortization Table");
   }
}
