package br.com.viasoft.livros.controle;

import br.com.viasoft.livros.dto.ProdutoFormularioDTO;
import br.com.viasoft.livros.model.Produto;
import br.com.viasoft.livros.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping("/produto")
    public String listaProduto(Model model) {
        List<Produto> lista = produtoService.findAll();
        model.addAttribute("lista", lista);
        return "produto/listaprodutos";
    }

    @GetMapping("/produto/autor/{autor}")
    public String listaProdutoByAutor(@PathVariable("autor")String autor, Model model)  {
        List<Produto> lista= produtoService.findByAutor(autor);
        model.addAttribute("lista", lista);
        return "produto/listaprodutos";
    }

    @GetMapping("/produto/edit/{id}")
    public String editaProduto(@PathVariable("id") Long id, ProdutoFormularioDTO produtoFormularioDTO, Model model){
        Produto produto = produtoService.findById(id).orElse(null);
        model.addAttribute("produto", produto);
        produtoFormularioDTO = new ProdutoFormularioDTO(produto);
        model.addAttribute("produto", produto);
        model.addAttribute("dto", produtoFormularioDTO);
        return "produto/suamae";
    }

    @GetMapping("/produto/{id}")
     public String listaProdutoById(@PathVariable("id") Long id, Model model){
        Produto produto = produtoService.findById(id).orElse(null);

        model.addAttribute("produto", produto);
        return "produto/produtodetail";
    }

    @GetMapping("/produto/novo")
    public String getProduto(ProdutoFormularioDTO produtoFormularioDTO){
        return "produto/formularioproduto";
    }

    @PostMapping("/produto/salvar")
    public String formProduto(@Valid ProdutoFormularioDTO produtoDTO, BindingResult result) {
        if (result.hasErrors()){
            return "produto/formularioproduto";
        }
        Produto produto = produtoDTO.toProduto();
        produtoService.save(produto);
        return "produto/formularioproduto";
    }
}
