package app;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import utils.ApplicationTime;

public class _1_Thema2 extends Animation {
    private static JLabel label;

    @Override
    public ArrayList<JFrame> createFrames(ApplicationTime applicationTimeThread) {
        // a list of all frames (windows) that will be shown
        ArrayList<JFrame> frames = new ArrayList<>();

        // Create main frame (window)
        JFrame frame = new JFrame("Vollständig elastische, teilelastische und vollständig inelastische Stöße zweier Massen in der Ebene");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new _1_Thema2Panel(applicationTimeThread);
        frame.add(panel);
        frame.pack(); // adjusts size of the JFrame to fit the size of it's components
        frame.setVisible(true);
        frames.add(frame);

        // Create second frame with label
        JFrame secondFrame = new JFrame("Wertefenster");
        secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel secondPanel = new JPanel();
        secondFrame.add(secondPanel);
        label = new JLabel("<html>Noch keine<br>Kollision bisher!</html>");
        secondPanel.add(label);
        secondFrame.pack();
        secondFrame.setSize(200, 330);
        secondFrame.setLocation(920, 0);
        secondFrame.setVisible(true);
        frames.add(secondFrame);

        return frames;
    }
    public static void updateLabelText(String newText) {
        label.setFont(label.getFont().deriveFont(20f));
        label.setText("<html>" + newText + "</html>");
    }
}
class _1_Thema2Panel extends JPanel {
    // panel has a single time tracking thread associated with it
    private final ApplicationTime t;
    private double time;
    public _1_Thema2Panel(ApplicationTime thread) {this.t = thread;}
    // set this panel's preferred size for auto-sizing the container JFrame
    public Dimension getPreferredSize() {
        return new Dimension(910, 510);
    }
    double startX1 = Eingabefenster.SecondaryGraphicsContent.startX1;
    double startY1 = Eingabefenster.SecondaryGraphicsContent.startY1;
    double vX1 = Eingabefenster.SecondaryGraphicsContent.vX1;
    double vY1 = Eingabefenster.SecondaryGraphicsContent.vY1;
    double currentX1 = startX1;
    double currentY1 = startY1;
    double collisionTime1 = 0.0;
    double masse1 = Double.parseDouble(Eingabefenster.SecondaryGraphicsContent.inputField2.getText());

    double startX2 = Eingabefenster.SecondaryGraphicsContent.startX2;
    double startY2 = Eingabefenster.SecondaryGraphicsContent.startY2;
    double vX2 = Eingabefenster.SecondaryGraphicsContent.vX2;
    double vY2 = Eingabefenster.SecondaryGraphicsContent.vY2;
    double currentX2 = startX2;
    double currentY2 = startY2;
    double collisionTime2 = 0.0;
    double masse2 = Double.parseDouble(Eingabefenster.SecondaryGraphicsContent.inputField3.getText());
    double koeffizient = Double.parseDouble(Eingabefenster.SecondaryGraphicsContent.inputField1.getText());
    double diameter = 40;
    double normal1[] = {1,1};
    double normal2[] = {-1,1};
    double normal3[] = {-1,-1};
    double normal4[] = {1,-1};
    double v[];
    double schwerpunktX;
    double schwerpunktY;
    double u1x; double u1y;     double u1xStrich; double u1yStrich;
    double u2x; double u2y;     double u2xStrich; double u2yStrich;
    double v1xStrich; double v1yStrich;     double v2xStrich; double v2yStrich;
    double Vx; double Vy;

    boolean col;
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        time = t.getTimeInSeconds();

        //=============Arena==============================================
        super.paintComponent(g);
        Graphics2D arena = (Graphics2D) g;
        int[] xPoints = {95, 815, 905, 905, 815, 95, 5, 5, 95};
        int[] yPoints = {5, 5, 95, 415, 505, 505, 415, 95, 5};
        arena.setStroke(new BasicStroke(4));
        arena.setColor(Color.BLACK);
        arena.drawPolyline(xPoints, yPoints, 9);

        //=============Ball 1=============================================

        // right wall bounce
        if(currentX1 >= 905-diameter) {
            vX1 = -vX1;
            startX1 = 904 - diameter;
            startY1 = currentY1;
            collisionTime1 = time;
        }
        // left wall bounce
        if(currentX1 <= 5){
            vX1 = -vX1;
            startX1 = 6;
            startY1 = currentY1;
            collisionTime1 = time;
        }
        // bottom wall bounce
        if(currentY1 >= 505-diameter){
            vY1 = -vY1;
            startY1 =  504 - diameter;
            startX1 = currentX1;
            collisionTime1 = time;
        }
        // top wall bounce
        if(currentY1 <= 5){
            vY1 = -vY1;
            startY1 = 6;
            startX1 = currentX1;
            collisionTime1 = time;
        }

        // diagonal top left bounce
        if(-currentX1 -currentY1 + 45 + diameter>0){
            v=kollision_Diagonale_ball(normal1, vX1, vY1);
            vX1=v[0];
            vY1=v[1];
            startX1 = currentX1 + 1;
            startY1 = currentY1 + 1;
            collisionTime1 = time;
        }
        // diagonal top right bounce
        if((-currentX1 + 810) - (90 - currentY1)+diameter<0){
            v=kollision_Diagonale_ball(normal2, vX1, vY1);
            vY1=v[1];
            vX1=v[0];
            startX1 = currentX1 - 1;
            startY1 = currentY1 + 1;
            collisionTime1 = time;
        }
        // diagonal bottom left bounceS
        if(currentX1 - currentY1 + 420 - diameter<0){
            v=kollision_Diagonale_ball(normal4, vX1, vY1);
            vY1=v[1];
            vX1=v[0];
            startX1 = currentX1 + 1;
            startY1 = currentY1 - 1;
            collisionTime1 = time;
        }
        // diagonal bottom right bounce
        if((-currentX1 + 810) - currentY1 + 470 - diameter<0){
            v=kollision_Diagonale_ball(normal3, vX1, vY1);
            vY1=v[1];
            vX1=v[0];
            startX1 = currentX1 - 1;
            startY1 = currentY1 - 1;
            collisionTime1 = time;
        }
        currentY1 = startY1 + (time - collisionTime1) * vY1;
        currentX1 = startX1 + (time - collisionTime1) * vX1;

        g.setColor(Color.RED);
        g.fillOval((int) currentX1, (int) currentY1, (int) diameter, (int) diameter);

        //=============Ball 2=============================================

        // right wall bounce
        if(currentX2 >= 905-diameter) {
            vX2 = -vX2;
            startX2 = 904 - diameter;
            startY2 = currentY2;
            collisionTime2 = time;
        }
        // left wall bounce
        if(currentX2 <= 5){
            vX2 = -vX2;
            startX2 = 6;
            startY2 = currentY2;
            collisionTime2 = time;
        }
        // bottom wall bounce
        if(currentY2 >= 505-diameter){
            vY2 = -vY2;
            startY2 =  504 - diameter;
            startX2 = currentX2;
            collisionTime2 = time;
        }
        // top wall bounce
        if(currentY2 <= 5){
            vY2 = -vY2;
            startY2 = 6;
            startX2 = currentX2;
            collisionTime2 = time;
        }

        // diagonal top left bounce
        if(-currentX2 -currentY2 + 45 + diameter>0){
            v=kollision_Diagonale_ball(normal1, vX2, vY2);
            vX2=v[0];
            vY2=v[1];
            startX2 = currentX2 + 1;
            startY2 = currentY2 + 1;
            collisionTime2 = time;
        }
        // diagonal top right bounce
        if((-currentX2 + 810) - (90 - currentY2)+diameter<0){
            v=kollision_Diagonale_ball(normal2, vX2, vY2);
            vY2=v[1];
            vX2=v[0];
            startX2 = currentX2 - 1;
            startY2 = currentY2 + 1;
            collisionTime2 = time;
        }
        // diagonal bottom left bounce
        if(currentX2 - currentY2 + 420 - diameter<0){
            v=kollision_Diagonale_ball(normal4, vX2, vY2);
            vY2=v[1];
            vX2=v[0];
            startX2 = currentX2 + 1;
            startY2 = currentY2 - 1;
            collisionTime2 = time;
        }
        // diagonal bottom right bounce
        if((-currentX2 + 810) - currentY2 + 470 - diameter<0){
            v=kollision_Diagonale_ball(normal3, vX2, vY2);
            vY2=v[1];
            vX2=v[0];
            startX2 = currentX2 - 1;
            startY2 = currentY2 - 1;
            collisionTime2 = time;
        }
        currentY2 = startY2 + (time - collisionTime2) * vY2;
        currentX2 = startX2 + (time - collisionTime2) * vX2;

        g.setColor(Color.BLUE);
        g.fillOval((int) currentX2, (int) currentY2, (int) diameter, (int) diameter);

        //=============Verbindungslinien==================================
        Graphics2D linie = (Graphics2D) g;
        linie.setColor(Color.BLACK);
        int strokeWidth = 5;
        linie.setStroke(new BasicStroke(strokeWidth));
        linie.drawLine((int) currentX1 + (int) diameter/2, (int) currentY1 + (int) diameter/2, (int) currentX2 + (int) diameter/2, (int) currentY2 + (int) diameter/2);

        //=============Schwerpunkt========================================
        schwerpunktX = ((((currentX1 + (diameter / 2)) * masse1) + ((currentX2 + (diameter / 2)) * masse2)) / (masse1 + masse2));
        schwerpunktY = ((((currentY1 + (diameter / 2)) * masse1) + ((currentY2 + (diameter / 2)) * masse2)) / (masse1 + masse2));

        g.setColor(Color.GREEN);
        g.fillOval((int) schwerpunktX - 10 / 2, (int) schwerpunktY - 10 / 2, 10, 10);

        //=============Bälle Kollision====================================
        double b = Math.sqrt(Math.pow(currentX2 - currentX1, 2) + Math.pow(currentY2 - currentY1, 2));
        if(diameter >= b){
            kollision_ball_mit_ball();
        }
    }
    public static double[] kollision_Diagonale_ball(double[] n,double vX, double vY) {
        double eps1=1;
        double nn[]= {n[0]/Math.sqrt(n[0]*n[0]+n[1]*n[1]),n[1]/Math.sqrt(n[0]*n[0]+n[1]*n[1])};
        double v[]= {vX,vY};
        double vsenk[]=new double[2];
        double vp[] = new double[2];

        double vPar=((v[0]*nn[0])+(v[1]*nn[1]))/((nn[0]*nn[0])+(nn[1]*nn[1]));
        vp[0]=vPar*nn[0];
        vp[1]=vPar*nn[1];

        vsenk[0]=v[0]-vp[0];
        vsenk[1]=v[1]-vp[1];

        double w[]= {-eps1*vp[0]+vsenk[0], -eps1*vp[1]+vsenk[1]};

        return w;
    }
    public void kollision_ball_mit_ball(){
        double V[]=cmv();
        double u[]=spgeschwindigkeit(bv());
        double par[]=par(bv());

        u1x = Math.round(((masse2/(masse1+masse2))*(vX1-vX2))*100.0)/100.0;
        u1y = Math.round(((masse2/(masse1+masse2))*(vY1-vY2))*100.0)/100.0;
        u2x = Math.round(((masse1/(masse1+masse2))*(vX2-vX1))*100.0)/100.0;
        u2y = Math.round(((masse1/(masse1+masse2))*(vY2-vY1))*100.0)/100.0;

        u1xStrich = Math.round(u[0]*100.0)/100.0;
        u1yStrich = Math.round(u[1]*100.0)/100.0;
        u2xStrich = Math.round(u[2]*100.0)/100.0;
        u2yStrich = Math.round(u[3]*100.0)/100.0;

        v1xStrich = Math.round((u[0]+V[0])*100.0)/100.0;
        v1yStrich = Math.round((u[1]+V[1])*100.0)/100.0;
        v2xStrich = Math.round((u[2]+V[0])*100.0)/100.0;
        v2yStrich = Math.round((u[3]+V[1])*100.0)/100.0;
        double bx = Math.round((currentX2 - currentX1) * 100.0) / 100.0;
        double by = Math.round((currentY2 - currentY1) * 100.0) / 100.0;
        if(!col) {
            String s = (
                    "bx:    " + Math.round((currentX2 - currentX1) * 100.0) / 100.0 + "  by:   " + Math.round((currentY2 - currentY1) * 100.0) / 100.0 + "<br>" +
                            "|b|:   " + Math.sqrt(Math.pow(bx, 2) + Math.pow(by, 2)) + "<br>" +
                            "   u1:  " + u1x + ",   " + u1y + "<br>" +
                            "   u2:  " + u2x + ",   " + u2y + "<br>" +
                            "   u1´: " + u1xStrich + ",     " + u1yStrich + "<br>" +
                            "   u2´: " + u2xStrich + ",     " + u2yStrich + "<br>" +
                            "   v1:  " + Math.round(vX1 * 100.0) / 100.0 + ",     " + Math.round(vY1 * 100.0) / 100.0 + "<br>" +
                            "   v2:  " + Math.round(vX2 * 100.0) / 100.0 + ",     " + Math.round(vY2 * 100.0) / 100.0 + "<br>" +
                            "   v1´: " + v1xStrich + ",     " + v1yStrich + "<br>" +
                            "   v2´: " + v2xStrich + ",     " + v2yStrich + "<br>" +
                            "   V:   " + Vx + ",        " + Vy + "<br>"
            );
            _1_Thema2.updateLabelText(s);
            col = true;
        }

        vX1=u[0]+V[0];
        vY1=u[1]+V[1];
        startX1=currentX1+par[2]        *3;   //Offset 3
        startY1=currentY1+par[3]*3;
        collisionTime1 = time;

        vX2=u[2]+V[0];
        vY2=u[3]+V[1];
        startX2=currentX2+par[0]*3;   //Offset 3
        startY2=currentY2+par[1]*3;
        collisionTime2 = time;
    }
    public double[] cmv() {    // Schwerpunktgeschwindigkeit
        double V[]=new double[2];
        V[0]=(1/(masse1+masse2))*(masse1*vX1+masse2*vX2);
        V[1]=(1/(masse1+masse2))*(masse1*vY1+masse2*vY2);
        Vx = Math.round(V[0]*100.0)/100.0;
        Vy = Math.round(V[1]*100.0)/100.0;
        return V;
    }

    public double[] spgeschwindigkeit(double []n) {                   //Schwerpunktgeschwindigkeit u1´ , u2´
        double nn[]= {n[0]/Math.sqrt(n[0]*n[0]+n[1]*n[1]),n[1]/Math.sqrt(n[0]*n[0]+n[1]*n[1])};
        double v[]= cms();
        double usenk[]=new double[4];
        double upar[]=new double[4];

        double u1par=((v[0]*nn[0])+(v[1]*nn[1]))/((nn[0]*nn[0])+(nn[1]*nn[1]));  //u1
        upar[0]=u1par*nn[0];
        upar[1]=u1par*nn[1];

        usenk[0]=v[0]-(upar[0]);
        usenk[1]=v[1]-(upar[1]);

        double u2par=((v[2]*nn[0])+(v[3]*nn[1]))/((nn[0]*nn[0])+(nn[1]*nn[1]));  //u2
        upar[2]=u2par*nn[0];
        upar[3]=u2par*nn[1];

        usenk[2]=v[2]-(upar[2]);
        usenk[3]=v[3]-(upar[3]);

        double []u= {usenk[0]-(koeffizient*upar[0]),usenk[1]-(koeffizient*upar[1]),     //u1´
                    usenk[2]-(koeffizient*upar[2]),usenk[3]-(koeffizient*upar[3])};		//u2´

        return u;
    }
    public double[] bv() {
        double xdiff= ((currentX2)-(currentX1));
        double ydiff= ((currentY2)-(currentY1));
        double values[]= {xdiff,ydiff};

        return values ;
    }
    public double []cms(){

        double u[]=new double[4];

        u[0]=(masse2/(masse1+masse2))*(vX1-vX2);			//u1
        u[1]=(masse2/(masse1+masse2))*(vY1-vY2);

        u[2]=(masse1/(masse1+masse2))*(vX2-vX1);			//u2
        u[3]=(masse1/(masse1+masse2))*(vY2-vY1);

        return u;
    }
    public double[] par(double[] n) {
        double nn[]= {n[0],n[1]};
        double v[]= cms();
        double upar[] = new double[4];

        double u1par=((v[0]*nn[0])+(v[1]*nn[1]))/((nn[0]*nn[0])+(nn[1]*nn[1]));  //u1
        upar[0]=u1par*nn[0];
        upar[1]=u1par*nn[1];

        double u2par=((v[2]*nn[0])+(v[3]*nn[1]))/((nn[0]*nn[0])+(nn[1]*nn[1]));  //u2
        upar[2]=u2par*nn[0];
        upar[3]=u2par*nn[1];

        upar[0]=upar[0]/ Math.sqrt(upar[0]*upar[0]+upar[1]*upar[1]);
        upar[1]=upar[1]/Math.sqrt(upar[0]*upar[0]+upar[1]*upar[1]);
        upar[2]=upar[2]/Math.sqrt(upar[2]*upar[2]+upar[3]*upar[3]);
        upar[3]=upar[3]/Math.sqrt(upar[2]*upar[2]+upar[3]*upar[3]);

        return upar;
    }
}
