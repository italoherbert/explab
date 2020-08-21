package italo.explab.arvore.exp.obj;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public class SuperObjeto extends Exp {
    
    private String classeNome;
    private SuperObjeto superObjeto;

    @Override
    public Instrucao novo( ExecNo parent ) {
        SuperObjeto sobj = new SuperObjeto();
        super.carrega( sobj, parent );
           
        sobj.setClasseNome( classeNome );
        sobj.superObjeto = (SuperObjeto)superObjeto.novo( sobj );        
                        
        return sobj;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        
    }

    public String getClasseNome() {
        return classeNome;
    }

    public void setClasseNome(String classeNome) {
        this.classeNome = classeNome;
    }

    public SuperObjeto getSuperObjeto() {
        return superObjeto;
    }

    public void setSuperObjeto(SuperObjeto superObjeto) {
        this.superObjeto = superObjeto;
    }
    
}
