package italo.explab.inter.estruturas.tentecapture;

import italo.explab.ErroMSGs;
import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.msg.Erro;
import italo.explab.recursos.ClasseManager;
import italo.explab.recursos.classe.Classe;
import italo.explab.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;
import italo.explab.AnaliseOuInterResult;
import italo.explab.InterAplic;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.estruturas.node.Capture;
import italo.explab.arvore.estruturas.node.TenteCapture;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.estruturas.ComandoInter;

public class TenteCaptureInter extends ComandoInter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {              
        ContadorUtil contUtil = aplic.getContUtil();
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
                                
        AnaliseResult aresult = asManager.getTenteCaptureAnalisador().analisa( codigo, i );
        if ( aresult.getJ() == 0 )
            return new InterResult( aresult );
                                
        int j = 0;                
        
        int cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.TENTE );
        if ( cont == 0 )
            return new InterResult();
        
        TenteCapture tenteCapture = aplic.getExecutor().getExecManager().getExecNoFactory().getEstruturaFactory().novoTenteCapture( i, no, codigo );
        
        j += cont;
        j += contUtil.contaEsps( codigo, i+j );
        
        InterResult result = super.interpretaBloco( tenteCapture, base, aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();
        
        List<Capture> caps = new ArrayList();
        
        j += contUtil.contaEsps( codigo, i+j );
        cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.CAPTURE );
        while ( cont > 0 ) {            
            ProcCaptureInterResult proc = this.procCapture( aplic, codigo, i+j );
            if ( proc.getJ() == 0 )
                return proc;
            
            Capture capture = aplic.getExecutor().getExecManager().getExecNoFactory().getEstruturaFactory().novoCapture( i+j, tenteCapture, codigo );
            capture.setObjetoExNome( proc.getExClasseVarNome() ); 
            capture.setObjetoExNomeI( i+j ); 
            capture.setExClassesNomes( proc.getEXClasses() ); 
                        
            j += proc.getJ();
            
            result = super.interpretaBloco( capture, capture, aplic, codigo, i+j, i2 );
            if ( result.getJ() == 0 )
                return result;
            
            j += result.getJ();
            j += contUtil.contaEsps( codigo, i+j );
            
            caps.add( capture );            
            
            cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.CAPTURE );        
        }
        
        Capture[] capturas = new Capture[ caps.size() ];
        capturas = caps.toArray( capturas );
        
        tenteCapture.setCaptures( capturas ); 
                
        cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.FINALIZE );
        if ( cont > 0 ) {
            j += cont;
            j += contUtil.contaEsps( codigo, i+j );
            
            result = super.interpretaBloco( tenteCapture.getFinalize(), tenteCapture.getFinalize(), aplic, codigo, i+j, i2 );
            if ( result.getJ() == 0 )
                return result;
            
            j += result.getJ();
        }
                        
        return new InterResult( tenteCapture, j );
    }
    
    private ProcCaptureInterResult procCapture( InterAplic aplic, Codigo codigo, int i ) {
        ContadorUtil contUtil = aplic.getContUtil();
        ClasseManager classeManager = aplic.getClasseManager();
        
        int j = 0;

        int cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.CAPTURE );
        if ( cont == 0 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PALAVRA_RESERVADA_ESPERADA, PalavrasReservadas.CAPTURE );
            return new ProcCaptureInterResult( erro );
        }
        
        j += cont;
        j += contUtil.contaEsps( codigo, i+j );
        
        char ch = codigo.getSEGCH(i+j );
        if ( ch != '(' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_PARENTESES_ESPERADO );
            return new ProcCaptureInterResult( erro );
        }
        
        j++;
                
        List<String> capClasses = new ArrayList( 1 );
        String exceptionVarNome = null;
        
        boolean fim = false;
        while( !fim && codigo.isCHValido(i+j ) ) {
            j += contUtil.contaEsps( codigo, i+j );
            
            cont = contUtil.contaClasseOuPacoteNomeTam( codigo, i+j );
            if ( cont == 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_DE_EXCECAO_NOME_ESPERADO );
                return new ProcCaptureInterResult( erro );
            }
            
            String classeNome = codigo.getCodigo().substring( i+j, i+j+cont );            
            Classe classe = classeManager.buscaClasse( classeNome );
            if ( classe == null ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_NAO_ENCONTRADA, classeNome );
                return new ProcCaptureInterResult( erro );
            }
            
            capClasses.add( classe.getNomeCompleto() );
            
            j += cont;
            j += contUtil.contaEsps(codigo, i+j );
            
            ch = codigo.getSEGCH(i+j );
            if ( ch == '|' ) {
                j++;
                continue;
            }
                                  
            cont = contUtil.contaVarNomeTam( codigo, i+j );
            if ( cont == 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VAR_NOME_ESPERADO );
                return new ProcCaptureInterResult( erro );
            }
            
            exceptionVarNome = codigo.getCodigo().substring( i+j, i+j+cont );
                        
            j += cont;
            j += contUtil.contaEsps( codigo, i+j );
            
            
            ch = codigo.getSEGCH( i+j );
            if ( ch != ')' ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
                return new ProcCaptureInterResult( erro );
            }
            
            j++;
            fim = true;            
        }
        
        j += contUtil.contaEsps( codigo, i+j );
        if ( codigo.getSEGCH( i+j ) != '{' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_CHAVES_ESPERADO );
            return new ProcCaptureInterResult( erro );
        }
               
        if ( fim ) {
            String[] classes = new String[ capClasses.size() ];
            classes = capClasses.toArray( classes );
            
            return new ProcCaptureInterResult( exceptionVarNome, classes, j );
        }
        
        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
        return new ProcCaptureInterResult( erro );
    }
            
    class ProcCaptureInterResult extends InterResult {
        
        private String[] classes = null;
        private String exClasseVarNome = null;

        public ProcCaptureInterResult() {}

        public ProcCaptureInterResult( String exClasseVarNome, String[] classes, int j ) {
            super( j );
            this.exClasseVarNome = exClasseVarNome;
            this.classes = classes;
        }

        public ProcCaptureInterResult( Erro erro ) {
            super( erro );
        }

        public ProcCaptureInterResult( AnaliseOuInterResult result ) {
            super( result );
        }

        public String[] getEXClasses() {
            return classes;
        }

        public String getExClasseVarNome() {
            return exClasseVarNome;
        }        
        
    }
        
}
