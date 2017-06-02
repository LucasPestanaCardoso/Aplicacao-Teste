package br.com.cadastropessoa.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cadastropessoa.model.Pessoa;
import br.com.cadastropessoa.model.Relatorio;
import br.com.cadastropessoa.repository.PessoaRepository;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {

	@Autowired
	PessoaRepository pessoaRepository;

	private Relatorio relatorio = new Relatorio();

	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public @ResponseBody Pessoa salvar(@RequestBody Pessoa pessoa) {

		try {

			if (!pessoaRepository.registroDuplicado(pessoa.getNome())) {
				pessoaRepository.incluir(pessoa);
			} else {
				throw new Exception("Duplicado");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return pessoa;
	}

	@RequestMapping(value = "/gerarExcel", method = RequestMethod.POST)
	public @ResponseBody ArquivoResource gerarExcel(@RequestBody List<Pessoa> pessoa)
			throws SerialException, SQLException {

		try {

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Pessoas");

			int rowNum = 0;

			for (Pessoa ps : pessoa) {
				Row row = sheet.createRow(rowNum++);
				int colNum = 0;
				Cell cell = row.createCell(colNum++);
				cell.setCellValue(ps.getNome());
				Cell cell2 = row.createCell(colNum++);
				cell2.setCellValue(ps.getData());
				Cell cell3 = row.createCell(colNum++);
				cell3.setCellValue(ps.getCpf());
				Cell cell4 = row.createCell(colNum++);
				cell4.setCellValue(ps.getTelefone());
			}

			FileOutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") + "\\arquivo.xlsx");

			workbook.write(outputStream);
			workbook.close();

			relatorio.setNome("Pessoas");
			relatorio.setConteudo(outputStream.toString().getBytes(Charset.forName("UTF-8")));

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ArquivoResource(relatorio);
	}

	@RequestMapping(value = "/carregar", method = RequestMethod.GET)
	public @ResponseBody List<Pessoa> carregar() {

		return pessoaRepository.todosUsuarios();

	}

	@RequestMapping(value = "/excluirRegistro/{nome}", method = RequestMethod.DELETE)
	public @ResponseBody void excluir(@PathVariable String nome) {

		pessoaRepository.excluir(nome);

	}

	@RequestMapping(value = "/alterar", method = RequestMethod.POST)
	public @ResponseBody Pessoa alterar(@RequestBody Pessoa pessoa) {

		try {

			pessoaRepository.alterar(pessoa);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return pessoa;
	}

}
