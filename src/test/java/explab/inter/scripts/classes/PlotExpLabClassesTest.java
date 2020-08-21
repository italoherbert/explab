package explab.inter.scripts.classes;

import italo.explab.ExpLab;
import italo.explab.InterAplic;
import italo.explab.InterException;
import italo.explab.arvore.ExecResult;
import italo.explab.codigo.Codigo;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PlotExpLabClassesTest {
     
    private final ExpLab explab = new ExpLab();

    private final String[] CLASSES_ARQS = {
        "explab/plot/plot3d/PC3D.exp"
    };
    
    public PlotExpLabClassesTest() {
        try {
            explab.inicializa();
        } catch ( InterException ex ) {
            Logger.getLogger(PlotExpLabClassesTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
        
    @Test 
    public void execTest() {
        for( String arq : CLASSES_ARQS )
            this.execArq( arq ); 
                
        InterAplic aplic = explab.getAplic();
        
        Codigo codigo = new Codigo( aplic, "novo PC3D()" );
        InterResult result = aplic.getInterManager().getNovoObjetoInter().interpreta( aplic.getGrupoRaiz(), aplic.getGrupoRaiz(), aplic, codigo, 0, codigo.getCodlen() );
        if ( result.getJ() == 0 ) {
            System.out.println( result.getErro().getChave() );
        }
        
        assertTrue( result.getJ() != 0, codigo.getCodigo() );
        
        ExecResult er = aplic.getExecutor().exec( aplic.getGrupoRaiz() );
        
        assertNull( er.getErro() );
    }
    
    public void execArq( String arq ) {
        InterAplic aplic = explab.getAplic();
        InterManager manager = aplic.getInterManager();
        
        String cod = "";
        try {
            cod = aplic.getArquivoUtil().ler( arq );
        } catch ( IOException ex ) {
            fail( ex.getMessage() );
        }                
        
        Codigo codigo = new Codigo( aplic, cod );
        InterResult result = manager.getLeituraClassesPacoteInter().interpreta( aplic.getGrupoRaiz(), aplic.getGrupoRaiz(), aplic, codigo, 0, codigo.getCodlen() );
        
        boolean ok = ( result.getJ() == codigo.getCodlen() );
                
        if ( !ok && result.getErro() != null )
            explab.getAplic().getMSGManager().enviaErro( result.getErro() );                    
                
        assertTrue( ok );        
    }
        
}
