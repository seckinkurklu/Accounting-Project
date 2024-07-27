
package com.cydeo.controller;

import com.cydeo.dto.InvoiceDto;
import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        //and land user to the "Edit Purchase Invoice" page
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
//    @PostMapping("/purchaseInvoices/update/{id}")
//    public String updatePurchaseInvoice(@ModelAttribute InvoiceDto invoiceDto, RedirectAttributes redirectAttributes ){
//
//        invoiceService.save(invoiceDto);
//        Long id = invoiceDto.getId();
//        redirectAttributes.addAttribute("id", id);
//        return "redirect:/purchaseInvoices/update/{id}";
//    }

//    @PostMapping("/purchaseInvoices/update/{id}/addProduct")
//    public String addInvoiceProduct(@PathVariable Long id, @ModelAttribute("newInvoiceProduct") InvoiceProductDto invoiceProductDto, RedirectAttributes redirectAttributes) {
//        InvoiceDto invoiceById = invoiceService.getInvoiceById(id);
//        invoiceProductDto.setInvoice(invoiceById);
//        invoiceProductService.createInvoiceProduct(mapperUtil.convert(invoiceProductDto, new InvoiceProduct()));
//        redirectAttributes.addAttribute("id", id);
//        return "redirect:/purchaseInvoices/update/{id}";
//    }
//    @GetMapping("/purchaseInvoices/addInvoiceProduct/{id}")
//    public String addInvoiceProduct(@PathVariable Long id, Model model){
//        model.addAttribute("newInvoiceProduct", new InvoiceProductDto());
//        model.addAttribute("products", invoiceProductService.getAllInvoiceProducts());
//        return "invoice/purchase-invoice-update";
//    }
//
//    @PostMapping ("/purchaseInvoices/addInvoiceProduct/{id}")
//    public String addInvoiceProduct(@ModelAttribute InvoiceProduct invoiceProduct, Model model){
//        invoiceProductService.createInvoiceProduct(invoiceProduct);
//        return "invoice/purchase-invoice-update";
//    }
//
//    @GetMapping("/purchaseInvoices/removeInvoiceProduct/{invoiceId}")
//    public String removeInvoiceProduct(@PathVariable Long id,  Model model){
//        invoiceProductService.deleteInvoiceProduct(id);
//        return "invoice/purchase-invoice-update";
//    }




}
