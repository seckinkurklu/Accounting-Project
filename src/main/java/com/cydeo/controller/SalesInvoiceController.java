package com.cydeo.controller;

import com.cydeo.dto.InvoiceDto;
import com.cydeo.enums.InvoiceType;
import com.cydeo.exception.InvoiceNotFoundException;
import com.cydeo.exception.ProductLowLimitAlertException;
import com.cydeo.exception.ProductNotFoundException;
import com.cydeo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {

    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;
    private final InvoiceProductService invoiceProductService;
    private final CompanyService companyService;

    public SalesInvoiceController(InvoiceService invoiceService,
                                  ClientVendorService clientVendorService,
                                  ProductService productService,
                                  InvoiceProductService invoiceProductService,
                                  CompanyService companyService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.productService = productService;
        this.invoiceProductService = invoiceProductService;
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String invoiceSalesDtoList(Model model) {
        model.addAttribute("invoices", invoiceService.listAllSalesInvoice());
        return "/invoice/sales-invoice-list";
    }

    @GetMapping("/approve/{id}")
    public String getApproveInvoice(@PathVariable("id") Long id) throws InvoiceNotFoundException {
        InvoiceDto invoiceDto = invoiceService.findById(id);
        invoiceService.approveSalesInvoice(invoiceDto, InvoiceType.SALES);
        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/create")
    public String createSalesInvoice(Model model) {

        InvoiceDto newInvoiceDto = new InvoiceDto();
        newInvoiceDto.setInvoiceNo(invoiceService.newInvoiceNo());

        model.addAttribute("newSalesInvoice", new InvoiceDto());
        model.addAttribute("clients", clientVendorService.listAllByCompanyTitle());
        model.addAttribute("invoiceNo", invoiceService.newInvoiceNo());
        model.addAttribute("date", LocalDate.now());
        return "invoice/sales-invoice-create";
    }

    @PostMapping("/create")
    public String saveSalesInvoice(@ModelAttribute("invoiceDto") InvoiceDto invoiceDto) {

        InvoiceDto saved = invoiceService.save(invoiceDto);
        return "redirect:/salesInvoices/list";

    }

    @PostMapping("/approve/{id}")
    public String approveSalesInvoice(@PathVariable("id") Long id){

        try {
            invoiceService.approve(id);
            invoiceProductService.checkForLowQuantityAlert(id);
        }catch (ProductNotFoundException | ProductLowLimitAlertException exception){
            return "redirect:/salesInvoices/error?message=" + exception.getMessage();
        }
        return "redirect:/salesInvoices/list";
    }

    @ExceptionHandler({ProductNotFoundException.class, ProductLowLimitAlertException.class})
    public String handleProductException(Exception e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }
}

  /*  @PostMapping("/create")
    public String saveSalesInvoice(@Valid @ModelAttribute("newSalesInvoice") InvoiceDto invoiceDto,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "invoice/sales-invoice-create";
        }
        InvoiceDto newInvoiceDto = invoiceService.save(invoiceDto);
        return "redirect:/salesInvoices/update/" + newInvoiceDto.getId();
    }

   */