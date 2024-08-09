
package com.cydeo.controller;


import com.cydeo.dto.InvoiceDto;
import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.ProductService;


import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PurchaseInvoiceController {

    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;
    private final MapperUtil mapperUtil;
    private final InvoiceProductService invoiceProductService;
    private final InvoiceRepository invoiceRepository;


    public PurchaseInvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService, ProductService productService, MapperUtil mapperUtil, InvoiceProductService invoiceProductService, InvoiceRepository invoiceRepository) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.productService = productService;
        this.mapperUtil = mapperUtil;
        this.invoiceProductService = invoiceProductService;
        this.invoiceRepository = invoiceRepository;

    }

    @GetMapping("/purchaseInvoices/list")
    public String invoicePurchaseDtoList(Model model) {
        model.addAttribute("invoices", invoiceService.listAllPurchaseInvoice());
        return "/invoice/purchase-invoice-list";
    }

    @GetMapping("/purchaseInvoices/create")
    public String createPurchaseInvoice(Model model) {
        model.addAttribute("invoice", new InvoiceDto());
        model.addAttribute("vendors", clientVendorService.listAllByCompanyTitle());
        model.addAttribute("invoiceNo", invoiceService.newInvoiceNo());
        model.addAttribute("date", LocalDate.now());
        return "invoice/purchase-invoice-create";
    }

    @PostMapping("/purchaseInvoices/create")
    public String savePurchaseInvoice(@ModelAttribute("invoiceDto") InvoiceDto invoiceDto) {
        InvoiceDto saved = invoiceService.save(invoiceDto);
        return "redirect:/purchaseInvoices/update/" + saved.getId();
    }

    @GetMapping("/purchaseInvoices/update/{id}") // to Add Product
    public String updatePurchaseInvoice(@PathVariable Long id, Model model) {

        model.addAttribute("invoice", invoiceService.getInvoiceById(id));
        model.addAttribute("vendors", clientVendorService.listAllByCompanyTitle());
        model.addAttribute("newInvoiceProduct", new InvoiceProduct());
        model.addAttribute("products", productService.listAllProducts());
        model.addAttribute("invoiceProducts", invoiceProductService.getAllInvoiceProducts()); // guncellenecek
        return "invoice/purchase-invoice-update";

    }

    @PostMapping("/purchaseInvoices/addInvoiceProduct/{id}")
    public String addProduct(@PathVariable("id") Long id, @ModelAttribute("newInvoiceProduct") InvoiceProductDto invoiceProductDto, RedirectAttributes redirectAttributes) {
        // İlgili invoice'ı al
        Invoice invoice = invoiceRepository.findById(id).get();
        // InvoiceProductDto'yu InvoiceProduct'a dönüştür
        InvoiceProduct newInvoiceProduct = mapperUtil.convert(invoiceProductDto, new InvoiceProduct());
        if (newInvoiceProduct == null || invoiceProductDto == null) {

            return "redirect:/purchaseInvoices/update";
        }
        // InvoiceProduct'ı ilgili invoice ile ilişkilendir
        newInvoiceProduct.setInvoice(invoice);
        InvoiceProductDto convertedInvoiceProductDto = mapperUtil.convert(newInvoiceProduct, new InvoiceProductDto());
        // InvoiceProduct'ı kaydet
        invoiceProductService.save(convertedInvoiceProductDto);
        // Redirect attributes ekle
        redirectAttributes.addAttribute("id", id);
        return "redirect:/purchaseInvoices/update/{id}";
    }

//order of methods has been modified to display appropriate page


    @GetMapping("/purchaseInvoices/removeInvoiceProduct/{id}/{invoiceProductId}")
    public String removeInvoiceProduct(@PathVariable("id") Long id, @PathVariable("invoiceProductId") Long invoiceProductId, Model model) {

        invoiceProductService.delete(invoiceProductId);

        return "redirect:/purchaseInvoices/update/{id}";
    }

    @GetMapping("/salesInvoices/delete/{id}")
    public String deleteFromSalesInvoiceList(@PathVariable Long id) {

        invoiceService.removeInvoiceById(id);
        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/purchaseInvoices/print/{id}")
    public String printPurchaseInvoice(@PathVariable("id") Long id, Model model) {

        InvoiceDto invoice = invoiceService.getInvoiceById(id);
        List<InvoiceProductDto> invoiceProductList = invoiceProductService.getAllInvoiceProductsById(id);
        model.addAttribute("invoice", invoice);
        model.addAttribute("company", invoice.getCompany());
        model.addAttribute("invoiceProducts", invoiceProductList);
        return "invoice/invoice_print";
    }

    @GetMapping("/salesInvoices/print/{id}")
    public String printSalesInvoice(@PathVariable("id") Long id, Model model) {
        InvoiceDto invoice = invoiceService.getInvoiceById(id);
        List<InvoiceDto> invoiceProductList = invoiceService.listAllSalesInvoice();

        model.addAttribute("invoice", invoice);
        model.addAttribute("company", invoice.getCompany());
        model.addAttribute("invoiceProducts", invoiceProductList);
        return "invoice/invoice_print";
    }


    @GetMapping("/purchaseInvoices/delete/{id}")
    public String deleteFromPurchaseInvoiceList(@PathVariable("id") Long id) {

        invoiceService.deletePurchaseInvoice(id);
        return "redirect:/purchaseInvoices/list";
    }
}

/**/

/**/


/**/