package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager manager;
	
	public BarChartModel getVendasModel() {
        BarChartModel model = new BarChartModel();
        model.setAnimate(true);
        
        ChartSeries vendas = new ChartSeries();
        vendas.setLabel("Vendas 2015");
        
		List<Venda> listaVendas = getVendas();
        for (Venda venda : listaVendas ) {
        	vendas.set(venda.getLivro().getTitulo(),venda.getQuantidade());
		}
        
        model.addSeries(vendas);
        
        return model;
    }
	
	public List<Venda> getVendas(){
		List<Venda> vendas = this.manager.createQuery("select v from Venda v", Venda.class).getResultList();
		return vendas;
	}
	
}
