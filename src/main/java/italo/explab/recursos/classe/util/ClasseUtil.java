package italo.explab.recursos.classe.util;

import italo.explab.ErroMSGs;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.var.Variavel;
import italo.explab.var.BooleanVar;
import italo.explab.var.FuncVar;
import italo.explab.var.NullVar;
import italo.explab.var.ObjetoVar;
import italo.explab.var.num.RealVar;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import java.awt.Color;

public class ClasseUtil {
    
    public Color corValor( RecursoManager recursos, VariavelNome varnome ) throws ClasseUtilException {                
        Variavel corVr = recursos.getVarLista().buscaLocal( varnome.getNome() );
        if ( corVr == null )
            return null;
                
        Objeto corObj = this.buscaObjeto( corVr.getVar(), "RGB", varnome, false );
        RecursoManager corObjRecursos = corObj.getRecursos();

        VariavelNome vnr = new VariavelNome( "r", varnome );
        VariavelNome vng = new VariavelNome( "g", varnome );
        VariavelNome vnb = new VariavelNome( "b", varnome );

        RealVar rvar = this.realValor( corObjRecursos, vnr );
        RealVar gvar = this.realValor( corObjRecursos, vng );
        RealVar bvar = this.realValor( corObjRecursos, vnb );            

        String vcr = this.variavelCompleta( vnr );
        String vcg = this.variavelCompleta( vng );
        String vcb = this.variavelCompleta( vnb );

        if ( rvar == null )
            throw new ClasseUtilException( ClasseUtilException.VALOR_NAO_NULO_ESPERADO, vcr );        
        if ( gvar == null )
            throw new ClasseUtilException( ClasseUtilException.VALOR_NAO_NULO_ESPERADO, vcg );        
        if ( bvar == null )
            throw new ClasseUtilException( ClasseUtilException.VALOR_NAO_NULO_ESPERADO, vcb );        

        int r = (int)rvar.getValor();
        int g = (int)gvar.getValor();
        int b = (int)bvar.getValor();

        if ( r < 0 || r > 255 )
            throw new ClasseUtilException( ClasseUtilException.RGB_FORA_DA_FAIXA, vcr );
        if ( g < 0 || g > 255 )
            throw new ClasseUtilException( ClasseUtilException.RGB_FORA_DA_FAIXA, vcg );
        if ( b < 0 || b > 255 )
            throw new ClasseUtilException( ClasseUtilException.RGB_FORA_DA_FAIXA, vcb );

        return new Color( r, g, b );                                         
    }
    
    public MatrizVar arrayValor( RecursoManager recursos, VariavelNome varnome ) throws ClasseUtilException {
        return this.arrayValor( recursos, varnome, true );
    }
    
    public MatrizVar arrayValor( RecursoManager recursos, VariavelNome varnome, boolean excecaoAtivada ) throws ClasseUtilException {
        MatrizVar matrizVar = this.matrizValor( recursos, varnome );
        if ( matrizVar == null )
            return null;
        
        String vc = this.variavelCompleta( varnome );
        if ( matrizVar.getNL() != 1 ) {
            if ( excecaoAtivada )
                throw new ClasseUtilException( ClasseUtilException.TIPO_INVALIDO, vc, "array", matrizVar.getStringTipo() );
            return null;
        }
        
        return matrizVar;
    }
    
    public MatrizVar matrizValor( RecursoManager recursos, VariavelNome varnome ) throws ClasseUtilException {
        return this.matrizValor( recursos, varnome, true );
    }
    
    public MatrizVar matrizValor( RecursoManager recursos, VariavelNome varnome, boolean excecaoAtivada ) throws ClasseUtilException {
        String vc = this.variavelCompleta( varnome );
        
        Variavel matrizVar = recursos.getVarLista().buscaLocal( varnome.getNome() );
        if ( matrizVar == null )
            return null;
            
        if ( matrizVar.getVar().getTipo() == Var.MATRIZ )
            return (MatrizVar)matrizVar.getVar();        
                
        if ( excecaoAtivada )
            throw new ClasseUtilException( ClasseUtilException.TIPO_INVALIDO, vc, "matriz", matrizVar.getVar().getStringTipo() );
        
        return null;
    }
    
    public RealVar realValor( RecursoManager recursos, VariavelNome varnome ) throws ClasseUtilException {
        return this.realValor( recursos, varnome, true );
    }
    
    public RealVar realValor( RecursoManager recursos, VariavelNome varnome, boolean excecaoAtivada ) throws ClasseUtilException {        
        Variavel realVr = recursos.getVarLista().buscaLocal( varnome.getNome() );
        if ( realVr == null )
            return null;
        
        return this.realValor( realVr.getVar(), varnome, excecaoAtivada );        
    }

    public RealVar realValor( Var var, VariavelNome varnome ) throws ClasseUtilException {
        return this.realValor( var, varnome, true );
    }
    
    public RealVar realValor( Var var, VariavelNome varnome, boolean excecaoAtivada ) throws ClasseUtilException {
        if ( var.getTipo() == Var.REAL )
            return (RealVar)var;
        
        String vc = this.variavelCompleta( varnome );
        
        if ( excecaoAtivada )
            throw new ClasseUtilException( ClasseUtilException.TIPO_INVALIDO, vc, "real", var.getStringTipo() );
        return null;
    }
    
    public StringVar stringValor( RecursoManager recursos, VariavelNome varnome ) throws ClasseUtilException {
        return this.stringValor( recursos, varnome, true );
    }
    
    public StringVar stringValor( RecursoManager recursos, VariavelNome varnome, boolean excecaoAtivada ) throws ClasseUtilException {
        String vc = this.variavelCompleta( varnome );
        
        Variavel stringVar = recursos.getVarLista().buscaLocal( varnome.getNome() );
        if ( stringVar == null )
            return null;
        
        if ( stringVar.getVar().iguais( new NullVar() ) )
            return null;
        
        if ( stringVar.getVar().getTipo() == Var.STRING )
            return (StringVar)stringVar.getVar();
                
        if ( excecaoAtivada )
            throw new ClasseUtilException( ClasseUtilException.TIPO_INVALIDO, vc, "string", stringVar.getVar().getStringTipo() );
        return null;
    }
    
    public BooleanVar booleanValor( RecursoManager recursos, VariavelNome varnome ) throws ClasseUtilException {
        String vc = this.variavelCompleta( varnome );
        
        Variavel booleanVar = recursos.getVarLista().buscaLocal( varnome.getNome() );
        if ( booleanVar == null )
            return null;
        
        if ( booleanVar.getVar().getTipo() == Var.BOOLEAN )
            return (BooleanVar)booleanVar.getVar();
        
        throw new ClasseUtilException( ClasseUtilException.TIPO_INVALIDO, vc, "boolean", booleanVar.getVar().getStringTipo() );
    }

    public ObjetoVar objetoRefValor( RecursoManager recursos, VariavelNome varnome ) throws ClasseUtilException {
        return this.objetoRefValor( recursos, varnome, true );
    }
            
    public ObjetoVar objetoRefValor( RecursoManager recursos, VariavelNome varnome, boolean excecaoAtivada ) throws ClasseUtilException {
        String vc = this.variavelCompleta( varnome );
        
        Variavel objvar = recursos.getVarLista().buscaLocal( varnome.getNome() );
        if ( objvar == null )
            return null;
        
        if ( objvar.getVar().getTipo() == Var.OBJETO_REF )            
            return (ObjetoVar)objvar.getVar();        
        
        if ( excecaoAtivada )
            throw new ClasseUtilException( ClasseUtilException.TIPO_INVALIDO, vc, "objeto", objvar.getVar().getStringTipo() );
        return null;
    }
    
    public FuncVar funcValor( RecursoManager recursos, VariavelNome varnome ) throws ClasseUtilException {
        return this.funcValor( recursos, varnome, true );
    }
    
    public FuncVar funcValor( RecursoManager recursos, VariavelNome varnome, boolean excecaoAtivada ) throws ClasseUtilException {
        String vc = this.variavelCompleta( varnome );
        
        Variavel funcVar = recursos.getVarLista().buscaLocal( varnome.getNome() );
        if ( funcVar == null )
            return null;        
     
        if ( funcVar.getVar().getTipo() == Var.FUNC )
            return (FuncVar)funcVar.getVar();
        
        if ( excecaoAtivada )
            throw new ClasseUtilException( ClasseUtilException.TIPO_INVALIDO, vc, "func", funcVar.getVar().getStringTipo() );
        
        return null;
    }

    public Objeto buscaObjeto( RecursoManager recursos, String classeNome, VariavelNome varnome ) throws ClasseUtilException {        
        return this.buscaObjeto( recursos, classeNome, varnome, true );
    }
    
    public Objeto buscaObjeto( Var var, String classeNome, VariavelNome varnome ) throws ClasseUtilException {        
        return this.buscaObjeto( var, classeNome, varnome, true );
    }
    
    public Objeto buscaObjeto( RecursoManager recursos, String classeNome, VariavelNome varnome, boolean excecaoAtiva ) throws ClasseUtilException {        
        Variavel v = recursos.getVarLista().buscaLocal( varnome.getNome() );
        if ( v == null )
            return null;
        return this.buscaObjeto( v.getVar(), classeNome, varnome, excecaoAtiva );
    }
    
    public Objeto buscaObjeto( Var var, String classeNome, VariavelNome varnome, boolean excecaoAtiva ) throws ClasseUtilException {        
        if ( var.getTipo() == Var.OBJETO_REF ) {
            ObjetoVar objvar = (ObjetoVar)var;
            Objeto obj = objvar.getObjeto();
            if ( obj != null )
                if ( obj.instanciaDe( classeNome ) )
                    return obj;
        }
        
        if ( excecaoAtiva ) {
            String vc = this.variavelCompleta( varnome );
            throw new ClasseUtilException( ClasseUtilException.INSTANCIA_DE_CLASSE_ESPERADA, vc, classeNome );      
        }
        return null;
    }
    
    public String variavelCompleta( VariavelNome varnome ) {
        String nome = varnome.getNome();
        if ( varnome.getParente() != null )
            nome = this.variavelCompleta( varnome.getParente() ) + "." + nome;
        return nome;
    }
    
    public CodigoErro geraErro( Codigo codigo, ClasseUtilException e ) {
        int tipo = e.getTipo();
        String[] params = e.getParams();
        switch( tipo ) {
            case ClasseUtilException.RGB_FORA_DA_FAIXA:
                return new CodigoErro( this.getClass(), codigo, ErroMSGs.RGB_FORA_DA_FAIXA, params );
            case ClasseUtilException.VALOR_NAO_NULO_ESPERADO:
                return new CodigoErro( this.getClass(), codigo, ErroMSGs.VALOR_NAO_NULO_ESPERADO, params );
            case ClasseUtilException.TIPO_INVALIDO:
                return new CodigoErro( this.getClass(), codigo, ErroMSGs.VAR_TIPO_INVALIDO, params );
            case ClasseUtilException.INSTANCIA_DE_CLASSE_ESPERADA:
                return new CodigoErro( this.getClass(), codigo, ErroMSGs.CLASSE_INSTANCIA_ESPERADA, params );
            case ClasseUtilException.INSTANCIA_DE_UMA_DAS_CLASSES_ESPERADA:
                return new CodigoErro( this.getClass(), codigo, ErroMSGs.CLASSE_INSTANCIA_DE_UMA_ESPERADA, params );
            case ClasseUtilException.VETORES_TAMS_DIFERENTES:
                return new CodigoErro( this.getClass(), codigo, ErroMSGs.VET_TAMS_DIFERENTES, params );
            case ClasseUtilException.PERSONALIZADO:
                return new CodigoErro( this.getClass(), codigo, e.getErroChave(), params );
        }
        return null;
    }
    
}
