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
           
        IntVar[] vchoco;   
        IntVar k;
        int l=1; //nivel
        IntVar[] a;
        IntVar[] cuenta;
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
                    columna = new IntVar[Q];
                    columna[j]=a[i+j*R];
                }
                IntVar sum1=VariableFactory.enumerated(Cr[i+1]+"", Cr[i+1], Cr[i+1], solver);
                solver.post(IntConstraintFactory.sum(columna,"<=",sum1));
             }
             //C3
            if(l!=1){
                vchoco= VariableFactory.enumeratedArray("vchoco" + i + "_" + j,p+1, 0, max, solver);
                for (int i=0;i<=l;i++)
                 {
                     solver.post(IntConstraintFactory.arithm(vchoco[i], "=", v[i])); // se supone q los ceros no se consideran
                 }
                
            }
            
            cuenta = new IntVar[Q * R];//contar a 
                for(int i=0;i<Q*R;i++)
                {
                    LogicalConstraintFactory.ifThenElse(IntConstraintFactory.arithm(a[i], "=", l), //eq?¿
								IntConstraintFactory.arithm(cuenta[i], "=", 1),
								IntConstraintFactory.arithm(cuenta[i], "=", 0)
                );
                    
                    
                }
                
                //k=VariableFactory.enumerated("k", 0, Q*R, solver);
                k=VariableFactory.enumerated("k", 0, max, solver);
             //linkear k a cuantos ha contado
             IntVar uno =VariableFactory.enumerated("uno",1,1,solver);
             solver.post(IntConstraintFactory.count(uno,cuenta,k)
             //DUDA solver.post(IntConstraintFactory.arithm(k, "=", ));
             
             solver.post(IntConstraintFactory.arithm(vchoco[l], "=", k));
             //minimizar k
             solver.findOptimalSolution(ResolutionPolicy.MINIMAZE, k);
             
             //obtener vchoco y pasarlo a v
            
        }

//        if(solver.findSolution()){
//            do{
//            Chatterbox.printStatistics(solver);
//            Chatterbox.printSolutions(solver);
//            }while(solver.nextSolution());
//}
//        
    }
    
}
