
package com.cydeo.controller;

import com.cydeo.dto.InvoiceDto;
import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final InvoiceProductRepository invoiceProductRepository;
    private final InvoiceProductService invoiceProductService;
    private final MapperUtil mapperUtil;

    public InvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService, InvoiceProductRepository invoiceProductRepository, InvoiceProductService invoiceProductService, MapperUtil mapperUtil) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.invoiceProductRepository = invoiceProductRepository;
        this.invoiceProductService = invoiceProductService;
        this.mapperUtil = mapperUtil;
    }

    @GetMapping("/purchaseInvoices/list")
    public String invoicePurchaseDtoList(Model model) {
        model.addAttribute("invoices", invoiceService.listAllPurchaseInvoice());

        return "/invoice/purchase-invoice-list";
    }

    @GetMapping("/salesInvoices/list")
    public String invoiceSalesDtoList(Model model) {
        model.addAttribute("invoices", invoiceService.listAllSalesInvoice());
        return "/invoice/sales-invoice-list";
    }

    @GetMapping("/purchaseInvoices/create")
    public String createPurchaseInvoice(Model model){
        model.addAttribute("invoice", new InvoiceDto());
        model.addAttribute("vendors", clientVendorService.listAllByCompanyTitle());
        model.addAttribute("invoiceNo", invoiceService.newInvoiceNo());
        model.addAttribute("date", LocalDate.now());
        return "invoice/purchase-invoice-create";
    }

    @PostMapping("/purchaseInvoices/create")
    public String savePurchaseInvoice(@ModelAttribute ("invoiceDto")InvoiceDto invoiceDto){

        InvoiceDto saved = invoiceService.save(invoiceDto);

        //"Save" button should save the last created purchase invoice to the database,
        //and land user to the "Edit Purchase Invoice" page will
        Long id = saved.getId();
        return "redirect:/purchaseInvoices/update/"+id;
    }

    @GetMapping("/purchaseInvoices/update/{id}") // to Add Product
    public String updatePurchaseInvoice(@PathVariable Long id, Model model){

        model.addAttribute("invoice", invoiceService.getInvoiceById(id));
        model.addAttribute("vendors", clientVendorService.listAllByCompanyTitle());
        model.addAttribute("newInvoiceProduct", new InvoiceProductDto()); // added to new InvoiceProduct form
        model.addAttribute("products", invoiceProductService.getAllInvoiceProductsById(id)); //for new InvoiceProduct form
        model.addAttribute("invoiceProducts", invoiceProductService.getAllInvoiceProducts());// for Product List

        return "invoice/purchase-invoice-update";
    }

}
/**/

/**/
