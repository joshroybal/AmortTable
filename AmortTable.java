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
      setSize(750,600);
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

      table = new TextArea(30,70);
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
   {  double a = Double.parseDouble(amount.getText());
      int n = 12 * Integer.parseInt(noyears.getText());
      double r = Double.parseDouble(rate.getText()) / 1200.;

      double f = Math.pow((1. + r), n);
      double pmt = (a * f * r) / (f - 1.);   // monthly payment
      double total_interest = 0.F;

      NumberFormat currency = NumberFormat.getCurrencyInstance();
      String header = String.format("%5s%13s%11s%11s%11s%13s\n",
         "MONTH", "START   ", "PMT   ", "P   ", "I   ", "END   ");
      table.append(header);
      for (int month = 1; month <= n; month++)
      {  // calculate row values
         double start = a;
         double i = r * a;
         double p = pmt - i;
         a = a - p;
         total_interest += i;
         // write formatted values to row buffer
         StringBuffer row = new StringBuffer();
         row.append(String.format("%5s", Integer.toString(month)));
         row.append(String.format("%13s", currency.format(start)));
         row.append(String.format("%11s", currency.format(pmt)));
         row.append(String.format("%11s", currency.format(p)));
         row.append(String.format("%11s", currency.format(i)));
         row.append(String.format("%13s\n", currency.format(a)));
         table.append(row.toString()); // write to table
      }
      table.append("total interest paid = " + currency.format(total_interest));
   }
}

class AmortTable
{  public static void main(String[] args)
   {  new DisplayTable("Amortization Table");
   }
}
