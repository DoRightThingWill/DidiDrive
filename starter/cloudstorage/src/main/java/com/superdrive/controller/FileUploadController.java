package com.superdrive.controller;


import com.superdrive.storage.InputFile;
import com.superdrive.storage.StorageFileNotFoundException;
import com.superdrive.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/home")
	public String listUploadedFiles(Model model) throws IOException {

//		model.addAttribute("files", storageService.loadAll().map(
//				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//						"serveFile", path.getFileName().toString()).build().toUri().toString())
//				.collect(Collectors.toList()));

		ArrayList<InputFile> fileList = storageService.loadAllFiles();
		model.addAttribute("fileList", fileList);

		for(InputFile file : fileList){
			System.out.println(file);
		}

		return "home";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/home")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, Model model) {

		storageService.store(file);

//		redirectAttributes.addFlashAttribute("message",
//				"You successfully uploaded " + file.getOriginalFilename() + "!");


		ArrayList<InputFile> fileList = storageService.loadAllFiles();
		model.addAttribute("fileList", fileList);

		for(InputFile curFile : fileList){
			System.out.println(curFile);
		}

		return "redirect:/home";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
