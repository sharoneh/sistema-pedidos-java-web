/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pedidos.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("br.com.pedidos.validators.DescricaoUnicaProdutoValidator")
public class DescricaoUnicaProdutoValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {
        Object oldValue = ((UIInput) component).getValue();
        if (value != null ? value.equals(oldValue) : oldValue == null) {
            return;
        }
        
        String descricaoProduto = value.toString();
      
        if(descricaoProduto.equals("Teste")){
            FacesMessage msg = new FacesMessage("O produto " + descricaoProduto + " já está cadastrado.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg); 
        }
    }    
}
