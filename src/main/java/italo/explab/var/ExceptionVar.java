package italo.explab.var;

import italo.explab.msg.Erro;
import java.util.List;

public class ExceptionVar implements Var {

    private List<Erro> errosPilha;
    private String exVarNome;

    public ExceptionVar( String exVarNome, List<Erro> errosPilha ) {
        this.exVarNome = exVarNome;
        this.errosPilha = errosPilha;
    }
    
    @Override
    public Var nova() {
        return new ExceptionVar( exVarNome, errosPilha );
    }

    @Override
    public int getTipo() {
        return EXCEPTION;
    }

    @Override
    public int getTipoCompativel() {
        return TC_EXCEPTION;
    }

    @Override
    public String getStringTipo() {
        return TIPO_EXCEPTION;
    }

    @Override
    public boolean iguais(Var var) {
        if ( var.getTipo() == this.getTipo() ) {
            if ( ((ExceptionVar)var).getExVarNome().equalsIgnoreCase( exVarNome ) )
                if ( ((ExceptionVar)var).getErrosPilha().equals( errosPilha ))
                    return true;
            
            return false;
        }
        return false;
    }

    public List<Erro> getErrosPilha() {
        return errosPilha;
    }

    public void setErrosPilha(List<Erro> errosPilha) {
        this.errosPilha = errosPilha;
    }

    public String getExVarNome() {
        return exVarNome;
    }

    public void setExVarNome(String exVarNome) {
        this.exVarNome = exVarNome;
    }
    
}
