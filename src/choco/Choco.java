/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package choco;

import org.chocosolver.solver.ResolutionPolicy;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.trace.Chatterbox;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;

/**
 *
 * @author Richard
 */
public class Choco {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
/*
        Solver solver = new Solver("my first problem");
        4 // 2. Create variables through the variable factory
        5 IntVar x = VariableFactory.bounded("X", 0, 5, solver);
        6 IntVar y = VariableFactory.bounded("Y", 0, 5, solver);
        7 // 3. Create and post constraints by using constraint factories
        8 solver.post(IntConstraintFactory.arithm(x, "+", y, "<", 5));
        9 // 4. Define the search strategy
        10 solver.set(IntStrategyFactory.lexico_LB(x, y));
        11 // 5. Launch the resolution process
        12 solver.findSolution();
        13 //6. Print search statistics
        */
        
        //datos iniciales.
        int Q=4;
        int Qf[]={0,4,4,5,2};
        int max1=0;
        for(int i=1; i<Q+1;i++)
            if(Qf[i]>max1)
                max1=Qf[i];//frec maxima
        
        int R=5;
        int Cr[]={0,4,2,4,3,2};
        int max2=0;
        for(int i=1; i<Q+1;i++)
            if(Cr[i]>max2)
                max2=Cr[i];//recuso maximo
        
        int max=0;
        if (max1>max2)
                max=max1;
        else max=max2; //maximo de los maximos
       
        int p=0;
        for(int i=0; i<Q+1;i++)
           p=p+Qf[i]; //poblacion total
     
        int v[]= new int[p+1];//vector de anonimicidad
        for(int i=0; i<p+1;i++)
           v[i]=0;
        int suma=0;
        for(int i=0; i<p+1;i++)
           suma=suma+v[i]*i;
        
        int l=1; //nivel
        IntVar[] a;
        while (suma != p){
            
            Solver solver = new Solver("Minimaze K");
            a = new IntVar[Q * R];//matriz plana
            for (int i = 0; i < Q; i++) {
                for (int j = 0; j < R; j++) {
                    a[i * R + j] = VariableFactory.enumerated("a" + i + "_" + j, 0, max, solver);
                }
             }
            
            //restricciones
            IntVar[] fila = null;
            IntVar[] columna = null;
            //C1
             for (int i = 0; i < Q; i++) {
                for (int j = 0; j < R; j++) {
                    fila = new IntVar[R];
                    fila[j]=a[i*R+j];
                }
                IntVar sum=VariableFactory.enumerated(Qf[i+1]+"", Qf[i+1], Qf[i+1], solver);
                solver.post(IntConstraintFactory.sum(fila,sum));
             }
             //C2
             for (int i = 0; i < R; i++) {
                for (int j = 0; j < Q; j++) {
                    columna = new IntVar[R];
                    columna[j]=a[i+j*R];
                }
                IntVar sum=VariableFactory.enumerated(Cr[i+1]+"", Cr[i+1], Cr[i+1], solver);
                solver.post(IntConstraintFactory.sum(columna,"<=",sum));
             }
             //C3
       
          
            
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
//        
//        
//        Solver solver = new Solver("my first problem");
//        
//        IntVar x = VariableFactory.bounded("X", 0, 5, solver);
//        IntVar y = VariableFactory.bounded("Y", 0, 5, solver);
//        
//        IntVar xc = VariableFactory.scale(x,20);
//        IntVar yc = VariableFactory.scale(y,11);
//        IntVar xh = VariableFactory.scale(x,14);
//        IntVar yh = VariableFactory.scale(y,21);
//        IntVar xt = VariableFactory.scale(x,30);
//        IntVar yt = VariableFactory.scale(y,40);
//        
//        solver.post(IntConstraintFactory.arithm(xc, "+", yc, "<=", 130));
//        solver.post(IntConstraintFactory.arithm(xh, "+", yh, "<=", 400));
//        solver.post(IntConstraintFactory.arithm(xt, "+", yt, "<=", 150));
//        
//        
//        IntVar Beneficio = VariableFactory.bounded("objective", 0, 999999, solver);
//        solver.post(IntConstraintFactory.scalar(new IntVar[]{x,y}, new int[]{185,400}, Beneficio));
//        solver.findOptimalSolution(ResolutionPolicy.MAXIMIZE, Beneficio);
//            
//        if(solver.findSolution()){
//            do{
//            Chatterbox.printStatistics(solver);
//            Chatterbox.printSolutions(solver);
//            }while(solver.nextSolution());
//}
//        
    }
    
}
