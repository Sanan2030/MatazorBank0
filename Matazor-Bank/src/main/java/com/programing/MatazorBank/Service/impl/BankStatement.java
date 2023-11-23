package com.programing.MatazorBank.Service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.programing.MatazorBank.Dto.EmailDetails;
import com.programing.MatazorBank.Entity.Transaction;
import com.programing.MatazorBank.Entity.User;
import com.programing.MatazorBank.Repository.TransactionRepository;
import com.programing.MatazorBank.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
public class BankStatement {
   private final static String FILE="C:\\Users\\Sanan\\Documents\\BankStatement.pdf";
   private TransactionRepository transactionRepository;
   private UserRepository userRepository;
   private EmailService emailService;
   public List<Transaction> generateStatement(String accountNumber,String startDate,String endDate) throws FileNotFoundException, DocumentException {
       User user =userRepository.findByAccountNumber(accountNumber);
       String accountName=user.getFirstName()+" "+user.getLastName()+" "+user.getOtherName();
       LocalDate start=LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
       LocalDate end=LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
       List<Transaction> transactionList= transactionRepository.findAll().stream().
               filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
               .filter(transaction -> transaction.getCreatedAt().isEqual(start))
               .filter(transaction -> transaction.getCreatedAt().isEqual(end)).toList();
           Rectangle StatementSize=new Rectangle(PageSize.A4);
           Document document=new Document(StatementSize);
           OutputStream outputStream=new FileOutputStream(FILE);
           PdfWriter.getInstance(document,outputStream);
           document.open();
           PdfPTable bankInfoTable=new PdfPTable(1);
           PdfPCell bankName=new PdfPCell(new Phrase("MatazorBank"));
           bankName.setBorder(0);
           bankName.setBackgroundColor(BaseColor.BLUE);
           bankName.setPadding(20f);
           PdfPCell bankAddress = new PdfPCell(new Phrase("Sumqayit 10 mkr"));
           bankAddress.setBorder(0);
           bankInfoTable.addCell(bankName);
           bankInfoTable.addCell(bankAddress);
           PdfPTable statementInfo =new PdfPTable(2);
           PdfPCell customerInfo=new PdfPCell(new Phrase("StartDate info "+startDate));
           customerInfo.setBorder(0);
           PdfPCell statement=new PdfPCell(new Phrase("Statement of Account"));
           statement.setBorder(0);
           PdfPCell stopDate=new PdfPCell(new Phrase("End Date: "+endDate));
           stopDate.setBorder(0);
           PdfPCell name=new PdfPCell(new Phrase("Customer name: "+accountName));
           name.setBorder(0);
           PdfPCell space=new PdfPCell();
           space.setBorder(0);
           PdfPCell address=new PdfPCell(new Phrase("Customer Address "+user.getAddress()));
           address.setBorder(0);
           PdfPTable TransactionsTable=new PdfPTable(4);
           PdfPCell date= new PdfPCell(new Phrase("DATE"));
           date.setBackgroundColor(BaseColor.BLUE);
           date.setBorder(0);
           PdfPCell transactionType=new PdfPCell(new Phrase("TRANSACTION TYPE"));
           transactionType.setBackgroundColor(BaseColor.BLUE);
           transactionType.setBorder(0);
       PdfPCell transactionAmount=new PdfPCell(new Phrase("TRANSACTION AMOUNT"));
       transactionAmount.setBackgroundColor(BaseColor.BLUE);
       transactionAmount.setBorder(0);
           PdfPCell status=new PdfPCell(new Phrase("STATUS"));
           status.setBackgroundColor(BaseColor.BLUE);
           status.setBorder(0);
           TransactionsTable.addCell(date);
           TransactionsTable.addCell(transactionType);
           TransactionsTable.addCell(transactionAmount);
           TransactionsTable.addCell(status);
           transactionList.forEach(transaction -> {
               TransactionsTable.addCell(new Phrase(transaction.getCreatedAt().toString()));
               TransactionsTable.addCell(new Phrase(transaction.getTransactionType()));
               TransactionsTable.addCell(new Phrase(transaction.getAmount().toString()));
               TransactionsTable.addCell(new Phrase(transaction.getStatus()));
           });
           statementInfo.addCell(customerInfo);
           statementInfo.addCell(statement);
           statementInfo.addCell(endDate);
           statementInfo.addCell(name);
           statementInfo.addCell(space);
           statementInfo.addCell(address);
           document.add(bankInfoTable);
           document.add(statementInfo);
           document.add(TransactionsTable);
       document.close();
       EmailDetails emailDetails= EmailDetails.builder()
               .recipient(user.getEmail())
               .subject("STATEMENT OF ACCOUNT")
               .MessageBody("Your account Statement Atteched!")
               .attachment(FILE)
               .build();
       emailService.SendEmailAttachment(emailDetails);

       return transactionList;

   }

}