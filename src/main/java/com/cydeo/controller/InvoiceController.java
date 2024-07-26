package com.cydeo.controller;

import com.cydeo.dto.InvoiceDto;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.InvoiceService;
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

    public InvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
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
//        model.addAttribute("vendors", clientVendorService.listAllClientVendors());
        model.addAttribute("vendors", clientVendorService.listAllByCompanyTitle());
        model.addAttribute("invoiceNo", invoiceService.newInvoiceNo());
        model.addAttribute("date", LocalDate.now());
        return "invoice/purchase-invoice-create";
    }

    @PostMapping("/purchaseInvoices/create")
    public String savePurchaseInvoice(@ModelAttribute ("invoiceDto")InvoiceDto invoiceDto, RedirectAttributes redirectAttributes){

        InvoiceDto saved = invoiceService.save(invoiceDto);

        invoiceService.getId(saved.getInvoiceNo());

        //"Save" button should save the last created purchase invoice to the database,
        //and land user to the "Edit Purchase Invoice" page
        Long id = saved.getId();
        redirectAttributes.addAttribute("id", id);
        return "redirect:/purchaseInvoices/update/{id}";
    }

    @GetMapping("/purchaseInvoices/update/{id}")
    public String updatePurchaseInvoice(Model model, @PathVariable Long id){

        model.addAttribute("invoice", invoiceService.getInvoiceById(id));
        model.addAttribute("vendors", clientVendorService.listAllByCompanyTitle());
       return "invoice/purchase-invoice-update";

    }


}
