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
    private final ProductService productService;
    private final MapperUtil mapperUtil;
    private final InvoiceProductService invoiceProductService;
    private final InvoiceRepository invoiceRepository;


    public InvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService, ProductService productService, MapperUtil mapperUtil, InvoiceProductService invoiceProductService, InvoiceRepository invoiceRepository) {
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
    public String savePurchaseInvoice(@ModelAttribute ("invoice") InvoiceDto invoiceDto, RedirectAttributes redirectAttributes){

        InvoiceDto saved = invoiceService.save(invoiceDto);

        invoiceService.getId(saved.getInvoiceNo());

        //"Save" button should save the last created purchase invoice to the database,
        //and land user to the "Edit Purchase Invoice" page
//        Long id = saved.getId();
//        redirectAttributes.addAttribute("id", id);
        return "redirect:/purchaseInvoices/list";
    }

    @GetMapping("/purchaseInvoices/update/{id}")
    public String updatePurchaseInvoice(Model model, @PathVariable Long id){

        model.addAttribute("invoice", invoiceService.getInvoiceById(id));
        model.addAttribute("vendors", clientVendorService.listAllByCompanyTitle());
        model.addAttribute("newInvoiceProduct",new InvoiceProduct());
        model.addAttribute("products",productService.listAllProducts());
        model.addAttribute("invoiceProducts",invoiceProductService.getAllInvoiceProducts()); // guncellenecek
       return "invoice/purchase-invoice-update";

    }


//    @PostMapping("purchaseInvoices/addInvoiceProduct/{id}")
//    public String addProduct(@PathVariable("id")Long id,@ModelAttribute("newInvoiceProduct") InvoiceProductDto invoiceProductDto,RedirectAttributes redirectAttributes,Model model){
//
//        InvoiceDto invoice = invoiceService.getInvoiceById(id);
//
//
//        invoiceProductService.createInvoiceProduct(invoiceProductDto);
//
//        model.addAttribute("invoiceProducts",invoiceProductService.getAllInvoiceProducts());
//
//        redirectAttributes.addAttribute("id", id);
//
//
//        return "redirect:/purchaseInvoices/update/{id}";
//    }



    @PostMapping("/purchaseInvoices/addInvoiceProduct/{id}")
    public String addProduct(@PathVariable("id") Long id, @ModelAttribute("newInvoiceProduct") InvoiceProductDto invoiceProductDto,RedirectAttributes redirectAttributes) {
        // İlgili invoice'ı al
        Invoice invoice = invoiceRepository.findById(id).get();

        // InvoiceProductDto'yu InvoiceProduct'a dönüştür
        InvoiceProduct newInvoiceProduct = mapperUtil.convert(invoiceProductDto, new InvoiceProduct());

        if (newInvoiceProduct==null || invoiceProductDto==null){

            return "redirect:/purchaseInvoices/update";
        }


        // InvoiceProduct'ı ilgili invoice ile ilişkilendir
        newInvoiceProduct.setInvoice(invoice);

        InvoiceProductDto convertedInvoiceProductDto = mapperUtil.convert(newInvoiceProduct,new InvoiceProductDto());
        // InvoiceProduct'ı kaydet
        invoiceProductService.createInvoiceProduct(convertedInvoiceProductDto);
        // Redirect attributes ekle

        redirectAttributes.addAttribute("id",id);


        return "redirect:/purchaseInvoices/update/{id}";
    }








}
