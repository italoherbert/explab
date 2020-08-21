package italo.explab.func;

public abstract class AbstractFunc implements Func {
   
    @Override
    public boolean verifica(String nome, int quantParametros) {
        return nome.equalsIgnoreCase( this.getNome() ) && 
            ( quantParametros == this.getQuantParametros() || this.getQuantParametros() == -1 || quantParametros == -1 ) ;
    }
            
}
