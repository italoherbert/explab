package italo.explab.arvore.busca;

import italo.explab.arvore.busca.classe.ClasseBusca;
import italo.explab.arvore.busca.construtor.EsteConstrutorBusca;
import italo.explab.arvore.busca.construtor.SuperConstrutorBusca;
import italo.explab.arvore.busca.func.EscopoFuncBusca;
import italo.explab.arvore.busca.func.EsteFuncBusca;
import italo.explab.arvore.busca.func.EsteOuSuperFuncBusca;
import italo.explab.arvore.busca.func.FuncBusca;
import italo.explab.arvore.busca.func.LocalFuncBusca;
import italo.explab.arvore.busca.func.SuperFuncBusca;
import italo.explab.arvore.busca.var.EscopoVarBusca;
import italo.explab.arvore.busca.var.EsteOuSuperVarBusca;
import italo.explab.arvore.busca.var.EsteVarBusca;
import italo.explab.arvore.busca.var.LocalVarBusca;
import italo.explab.arvore.busca.var.SuperVarBusca;
import italo.explab.arvore.busca.var.VarBusca;

public class ArvBuscaManager {

    private final VarBusca esteVarBusca = new EsteVarBusca();
    private final VarBusca superVarBusca = new SuperVarBusca();
    private final VarBusca localVarBusca = new LocalVarBusca();
    private final VarBusca esteOuSuperVarBusca = new EsteOuSuperVarBusca();
    private final VarBusca escopoVarBusca = new EscopoVarBusca();
    
    private final FuncBusca esteFuncBusca = new EsteFuncBusca();
    private final FuncBusca superFuncBusca = new SuperFuncBusca();
    private final FuncBusca localFuncBusca = new LocalFuncBusca();
    private final FuncBusca esteOuSuperFuncBusca = new EsteOuSuperFuncBusca();
    private final FuncBusca escopoFuncBusca = new EscopoFuncBusca();
    
    private final ClasseBusca classeBusca = new ClasseBusca();
    private final EsteConstrutorBusca esteConstrutorBusca = new EsteConstrutorBusca();
    private final SuperConstrutorBusca superConstrutorBusca = new SuperConstrutorBusca();

    public VarBusca getEscopoVarBusca() {
        return escopoVarBusca;
    }

    public VarBusca getEsteVarBusca() {
        return esteVarBusca;
    }

    public VarBusca getSuperVarBusca() {
        return superVarBusca;
    }

    public VarBusca getLocalVarBusca() {
        return localVarBusca;
    }

    public FuncBusca getEscopoFuncBusca() {
        return escopoFuncBusca;
    }

    public FuncBusca getEsteFuncBusca() {
        return esteFuncBusca;
    }

    public FuncBusca getSuperFuncBusca() {
        return superFuncBusca;
    }

    public FuncBusca getLocalFuncBusca() {
        return localFuncBusca;
    }

    public ClasseBusca getClasseBusca() {
        return classeBusca;
    }

    public EsteConstrutorBusca getEsteConstrutorBusca() {
        return esteConstrutorBusca;
    }

    public SuperConstrutorBusca getSuperConstrutorBusca() {
        return superConstrutorBusca;
    }

    public VarBusca getEsteOuSuperVarBusca() {
        return esteOuSuperVarBusca;
    }

    public FuncBusca getEsteOuSuperFuncBusca() {
        return esteOuSuperFuncBusca;
    }
    
}
