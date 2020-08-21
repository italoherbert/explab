package italo.explab.recursos.var;

import italo.explab.var.Var;

public class Variavel {
    
    private final String nome;
    private Var var;
    
    private boolean ehsuper = false;         
    private boolean ehConstante = false;
        
    public Variavel( String nome, Var var ) {                
        this.nome = nome;
        this.var = var;        
    }
    
    public Variavel nova() {
        return new Variavel( nome, var.nova() );
    }
   
    public String getNome() {
        return nome;
    }

    public Var getVar() {
        return var;
    }

    public void setVar(Var var) {
        this.var = var;
    }

    public boolean isConstante() {
        return ehConstante;
    }

    public void setConstante( boolean ehConstante ) {
        this.ehConstante = ehConstante;
    }

    public boolean isSuper() {
        return ehsuper;
    }

    public void setSuper( boolean ehbase ) {
        this.ehsuper = ehbase;
    }
        
}
