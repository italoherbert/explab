package italo.explab.config.ajuda.nos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjudaXMLNo extends ElementoXMLNo {
    
    private final Map<String, PropriedadeXMLNo> propriedadesNos = new HashMap();
    private final List<PaginaXMLNo> paginaNos = new ArrayList();

    public AjudaXMLNo( ElementoXMLNo parente ) {
        super( parente );
    }
            
    public void addPropriedade( PropriedadeXMLNo propNo ) {
        propriedadesNos.put( propNo.getNome(), propNo );
    }
    
    public void addPaginaNo( PaginaXMLNo paginaNo ) {
        paginaNos.add( paginaNo );
    }
    
    public Map<String, PropriedadeXMLNo> getPropriedadeNos() {
        return propriedadesNos;
    }

    public List<PaginaXMLNo> getPaginaNos() {
        return paginaNos;
    }        

    @Override
    public int getTipo() {
        return AJUDA;
    }
    
}
