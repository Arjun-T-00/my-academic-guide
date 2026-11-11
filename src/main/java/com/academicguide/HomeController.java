package com.academicguide;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.Files;

@Controller
public class HomeController {

    private final NoteRepository noteRepository;
    private final TaskRepository taskRepository;
    private final SubjectRepository subjectRepository;
    private final PdfRepository pdfRepository;
    private final PdfStorageService pdfStorageService;

    public HomeController(NoteRepository noteRepository,TaskRepository taskRepository,SubjectRepository subjectRepository,
        PdfRepository pdfRepository,PdfStorageService pdfStorageService) {

        this.noteRepository = noteRepository;
        this.taskRepository = taskRepository;
        this.subjectRepository = subjectRepository;
        this.pdfRepository = pdfRepository;
        this.pdfStorageService = pdfStorageService;
    }

    @GetMapping("/")
    public String home(jakarta.servlet.http.HttpSession session,Model model) {
        model.addAttribute("studentName",session.getAttribute("studentName"));
        return "home";
    }

    
    // Display Notes
    @GetMapping("/notes")
    public String notes(
            Model model,
            jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        try {
            model.addAttribute("notes",
                    noteRepository.findAll(studentId));
        } catch (Exception e) {
            model.addAttribute("message",
                    "Unable to load saved notes.");
        }

        model.addAttribute("note", new Note());

        return "notes";
    }

    // Save or Update Note
    @PostMapping("/notes")
    public String saveNote(
            @ModelAttribute Note note,
            Model model,
            jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        try {
            if (note.getId() > 0) {
                noteRepository.update(note, studentId);
                model.addAttribute("message",
                        "Note updated successfully!");
            } else {
                noteRepository.save(note, studentId);
                model.addAttribute("message",
                        "Note saved successfully!");
            }

        } catch (Exception e) {
            model.addAttribute("message",
                    "Could not save the note. Please check the database connection.");
        }

        try {
            model.addAttribute("notes",
                    noteRepository.findAll(studentId));
        } catch (Exception e) {
            model.addAttribute("message",
                    "Could not load the notes list.");
        }

        model.addAttribute("note", new Note());

        return "notes";
    }

    // Edit Note
    @GetMapping("/notes/edit/{id}")
    public String editNote(
            @PathVariable int id,
            Model model,
            jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        try {
            Note note = noteRepository.findById(id, studentId);

            if (note == null) {
                return "redirect:/notes";
            }

            model.addAttribute("note", note);
            model.addAttribute("notes",
                    noteRepository.findAll(studentId));

        } catch (Exception e) {
            return "redirect:/notes";
        }

        return "notes";
    }

    // Delete Note
    @PostMapping("/notes/delete/{id}")
    public String deleteNote(
            @PathVariable int id,
            Model model,
            jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        try {
            noteRepository.deleteById(id, studentId);
            model.addAttribute("message",
                    "Note deleted successfully!");

        } catch (Exception e) {
            model.addAttribute("message",
                    "Could not delete the note.");
        }

        try {
            model.addAttribute("notes",
                    noteRepository.findAll(studentId));
        } catch (Exception e) {
            model.addAttribute("message",
                    "Could not load the notes.");
        }

        model.addAttribute("note", new Note());

        return "notes";
    }

    
    // Display Tasks
    @GetMapping("/tasks")
    public String tasks(
            Model model,
            jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        try {
            model.addAttribute("tasks",
                    taskRepository.findAll(studentId));
        } catch (Exception e) {
            model.addAttribute("message", "Unable to load tasks.");
        }

        model.addAttribute("task", new Task());

        return "tasks";
    }

    // Add or Update Task
    @PostMapping("/tasks")
    public String addTask(
            @ModelAttribute Task task,
            Model model,
            jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        try {
            if (task.getId() > 0) {
                taskRepository.update(task, studentId);
                model.addAttribute("message", "Task updated successfully!");
            } else {
                taskRepository.save(task, studentId);
                model.addAttribute("message", "Task added successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Could not save task.");
        }

        try {
            model.addAttribute("tasks",
                    taskRepository.findAll(studentId));
        } catch (Exception e) {
            model.addAttribute("message", "Could not load tasks.");
        }

        model.addAttribute("task", new Task());

        return "tasks";
    }

    // Complete or Undo Task
    @PostMapping("/tasks/toggle/{id}")
    public String toggleTask(
            @PathVariable int id,
            Model model,
            jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        try {
            taskRepository.toggleCompleted(id, studentId);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Could not update task status.");
        }

        try {
            model.addAttribute("tasks",
                    taskRepository.findAll(studentId));
        } catch (Exception e) {
            model.addAttribute("message", "Could not load tasks.");
        }

        model.addAttribute("task", new Task());

        return "tasks";
    }

    // Delete Task
    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(
            @PathVariable int id,
            Model model,
            jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        try {
            taskRepository.deleteById(id, studentId);
            model.addAttribute("message", "Task deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Could not delete task.");
        }

        try {
            model.addAttribute("tasks",
                    taskRepository.findAll(studentId));
        } catch (Exception e) {
            model.addAttribute("message", "Could not load tasks.");
        }

        model.addAttribute("task", new Task());

        return "tasks";
    }

    // Edit Task
    @GetMapping("/tasks/edit/{id}")
    public String editTask(
            @PathVariable int id,
            Model model,
            jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        try {
            Task task = taskRepository.findById(id, studentId);

            if (task == null) {
                return "redirect:/tasks";
            }

            model.addAttribute("task", task);
            model.addAttribute("tasks",
                    taskRepository.findAll(studentId));

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/tasks";
        }

        return "tasks";
    }

    @GetMapping("/pdfs")
    public String pdfLibrary(Model model,jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        model.addAttribute("subjects", subjectRepository.findAll(studentId));
        model.addAttribute("subject", new Subject());
        model.addAttribute("pdfs", pdfRepository.findAll(studentId));

        return "pdfs";
    }

    @PostMapping("/subjects")
    public String addSubject(@ModelAttribute Subject subject,jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        if (subject.getName() != null && !subject.getName().trim().isEmpty()) {

            subject.setName(subject.getName().trim());
            subjectRepository.save(subject, studentId);
        }

        return "redirect:/pdfs";
    }

    // Upload a PDF
    @PostMapping("/pdfs/upload")
    public String uploadPdf(
        @ModelAttribute Pdf pdf,
        MultipartFile file,
        Model model,
        jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        try {
            if (pdf.getTitle() == null || pdf.getTitle().trim().isEmpty()) {
                model.addAttribute("message", "Please enter a PDF title.");

            } else if (file == null || file.isEmpty()) {
                model.addAttribute("message", "Please select a PDF file.");

            } else if (subjectRepository.findAll(studentId).stream()
                    .noneMatch(subject -> subject.getId() == pdf.getSubjectId())) {
                model.addAttribute("message", "Please select a valid subject.");

            } else {
                String fileName = pdfStorageService.savePdf(file);

                pdf.setTitle(pdf.getTitle().trim());
                pdf.setFileName(fileName);

                pdfRepository.save(pdf, studentId);

                model.addAttribute("message", "PDF uploaded successfully!");
            }

        } catch (IOException e) {
            model.addAttribute("message", e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Could not upload PDF.");
        }

        model.addAttribute("subjects", subjectRepository.findAll(studentId));
        model.addAttribute("subject", new Subject());
        model.addAttribute("pdfs", pdfRepository.findAll(studentId));

        return "pdfs";
    }

    // Download PDF
    @GetMapping("/pdfs/download/{id}")
    public ResponseEntity<Resource> downloadPdf(
            @PathVariable int id,
            jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        try {
            Pdf pdf = pdfRepository.findById(id, studentId);

            if (pdf == null) {
                return ResponseEntity.notFound().build();
            }

            Path uploadDirectory = Paths.get("uploads")
                .toAbsolutePath()
                .normalize();

            Path filePath = uploadDirectory
                .resolve(pdf.getFileName())
                .normalize();

            if (!filePath.startsWith(uploadDirectory)) {
                return ResponseEntity.badRequest().build();
            }

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + pdf.getTitle() + ".pdf\"")
                .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Delete PDF
    @PostMapping("/pdfs/delete/{id}")
    public String deletePdf(
            @PathVariable int id,
            jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        try {
            // Find PDF only if it belongs to this student
            Pdf pdf = pdfRepository.findById(id, studentId);

            if (pdf != null) {

                Path uploadDirectory = Paths.get("uploads")
                    .toAbsolutePath()
                    .normalize();

                Path filePath = uploadDirectory
                    .resolve(pdf.getFileName())
                    .normalize();

                if (filePath.startsWith(uploadDirectory)) {
                    Files.deleteIfExists(filePath);
                }

                // Delete PDF record only for this student
                pdfRepository.deleteById(id, studentId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/pdfs";
    }

    // Show subject for editing
    @GetMapping("/subjects/edit/{id}")
    public String editSubject(
        @PathVariable int id,
        Model model,
        jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        Subject subject = subjectRepository.findById(id, studentId);

        if (subject == null) {
            return "redirect:/pdfs";
        }

        model.addAttribute("subjects", subjectRepository.findAll(studentId));
        model.addAttribute("pdfs", pdfRepository.findAll(studentId));
        model.addAttribute("subject", subject);
        model.addAttribute("editingSubject", true);

        return "pdfs";
    }

    // Update subject name
    @PostMapping("/subjects/update")
    public String updateSubject(
        @ModelAttribute Subject subject,
        jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        if (subject.getId() > 0 &&
            subject.getName() != null &&
            !subject.getName().trim().isEmpty()) {

            subject.setName(subject.getName().trim());
            subjectRepository.update(subject, studentId);
        }

        return "redirect:/pdfs";
    }

    // Delete subject only if it has no PDFs
    @PostMapping("/subjects/delete/{id}")
    public String deleteSubject(
        @PathVariable int id,
        jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        try {
            // Check whether this subject belongs to the student
            Subject subject = subjectRepository.findById(id, studentId);

            if (subject == null) {
                return "redirect:/pdfs";
            }

            // Check whether the subject contains PDFs
            List<Pdf> subjectPdfs =
                    pdfRepository.findBySubjectId(id, studentId);

            if (subjectPdfs.isEmpty()) {
                subjectRepository.deleteById(id, studentId);
            } else {
                return "redirect:/pdfs?error=subjectNotEmpty";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/pdfs?error=deleteFailed";
        }

        return "redirect:/pdfs?message=subjectDeleted";
    }

    // Update PDF title
    @PostMapping("/pdfs/update")
    public String updatePdf(
        @ModelAttribute Pdf pdf,
        jakarta.servlet.http.HttpSession session) {

        int studentId = (int) session.getAttribute("studentId");

        if (pdf.getId() > 0 &&
            pdf.getTitle() != null &&
            !pdf.getTitle().trim().isEmpty()) {

            pdf.setTitle(pdf.getTitle().trim());
            pdfRepository.update(pdf, studentId);
        }

        return "redirect:/pdfs";
    }
}