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
public class PruebaOptimizacion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //prueba123
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
        
        Solver solver = new Solver("Optimization Problem!");
        
        IntVar x = VariableFactory.bounded("X", 0, 5, solver);
        IntVar y = VariableFactory.bounded("Y", 0, 5, solver);
        
        IntVar xc = VariableFactory.scale(x,20);
        IntVar yc = VariableFactory.scale(y,11);
        IntVar xh = VariableFactory.scale(x,14);
        IntVar yh = VariableFactory.scale(y,21);
        IntVar xt = VariableFactory.scale(x,30);
        IntVar yt = VariableFactory.scale(y,40);
        
        solver.post(IntConstraintFactory.arithm(xc, "+", yc, "<=", 130));
        solver.post(IntConstraintFactory.arithm(xh, "+", yh, "<=", 400));
        solver.post(IntConstraintFactory.arithm(xt, "+", yt, "<=", 150));
        
        
        IntVar Beneficio = VariableFactory.bounded("objective", 0, 999999, solver);
        solver.post(IntConstraintFactory.scalar(new IntVar[]{x,y}, new int[]{185,400}, Beneficio));
        solver.findOptimalSolution(ResolutionPolicy.MAXIMIZE, Beneficio);
            
        if(solver.findSolution()){
            do{
            Chatterbox.printStatistics(solver);
            Chatterbox.printSolutions(solver);
            }while(solver.nextSolution());
}
        
    }
    
}
