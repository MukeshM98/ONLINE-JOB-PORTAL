import java.awt.*;
import java.util.*;
import java.util.List ;
class OnlineJobs extends Frame {

    private static Map<String, JobSeeker> jobSeekers = new HashMap<>();
    private static Map<String, Employer> employers = new HashMap<>();

    public OnlineJobs() {
        // Main Menu
        setTitle("Online Job Portal");
        setSize(400, 300);
        setLayout(new GridLayout(4, 1));

        Label welcomeLabel = new Label("Welcome to the Online Job Portal!", Label.CENTER);
        add(welcomeLabel);

        Button jobSeekerButton = new Button("Job Seeker");
        Button employerButton = new Button("Employer");
        Button exitButton = new Button("Exit");

        add(jobSeekerButton);
        add(employerButton);
        add(exitButton);

        // Button Actions
        jobSeekerButton.addActionListener(e -> openJobSeekerMenu());
        employerButton.addActionListener(e -> openEmployerMenu());
        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void openJobSeekerMenu() {
        Frame jobSeekerMenu = new Frame("Job Seeker Menu");
        jobSeekerMenu.setSize(400, 300);
        jobSeekerMenu.setLayout(new GridLayout(5, 1));

        Label jobSeekerLabel = new Label("Job Seeker Menu", Label.CENTER);
        jobSeekerMenu.add(jobSeekerLabel);

        Button registerButton = new Button("Register");
        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        jobSeekerMenu.add(registerButton);
        jobSeekerMenu.add(loginButton);
        jobSeekerMenu.add(backButton);

        // Button Actions
        registerButton.addActionListener(e -> openJobSeekerRegistration());
        loginButton.addActionListener(e -> openJobSeekerLogin());
        backButton.addActionListener(e -> jobSeekerMenu.dispose());

        jobSeekerMenu.setVisible(true);
    }

    private void openJobSeekerRegistration() {
        Frame registrationFrame = new Frame("Job Seeker Registration");
        registrationFrame.setSize(400, 400);
        registrationFrame.setLayout(new GridLayout(6, 2));

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        Label skillsLabel = new Label("Skills (comma-separated):");
        TextField skillsField = new TextField();

        Button registerButton = new Button("Register");
        Button cancelButton = new Button("Cancel");

        registrationFrame.add(nameLabel);
        registrationFrame.add(nameField);
        registrationFrame.add(emailLabel);
        registrationFrame.add(emailField);
        registrationFrame.add(passwordLabel);
        registrationFrame.add(passwordField);
        registrationFrame.add(skillsLabel);
        registrationFrame.add(skillsField);
        registrationFrame.add(registerButton);
        registrationFrame.add(cancelButton);

        // Button Actions
        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String skillsStr = skillsField.getText();

            List<String> skills = new ArrayList<>();
            for (String skill : skillsStr.split(",")) {
                skills.add(skill.trim());
            }

            JobSeeker seeker = new JobSeeker(name, email, password, skills);
            jobSeekers.put(email, seeker);

            showMessage("Job Seeker registered successfully!");
            registrationFrame.dispose();
        });

        cancelButton.addActionListener(e -> registrationFrame.dispose());

        registrationFrame.setVisible(true);
    }

    private void openJobSeekerLogin() {
        Frame loginFrame = new Frame("Job Seeker Login");
        loginFrame.setSize(400, 300);
        loginFrame.setLayout(new GridLayout(5, 1));

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();

        Button loginButton = new Button("Login");
        Button jobPreferencesButton = new Button("View Job Preferences");
        Button cancelButton = new Button("Cancel");

        loginFrame.add(emailLabel);
        loginFrame.add(emailField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.add(jobPreferencesButton);
        loginFrame.add(cancelButton);

        jobPreferencesButton.setEnabled(false);

        // Button Actions
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (jobSeekers.containsKey(email) && jobSeekers.get(email).getPassword().equals(password)) {
                showMessage("Login successful!");
                jobPreferencesButton.setEnabled(true);
            } else {
                showMessage("Invalid email or password. Please try again.");
            }
        });

        cancelButton.addActionListener(e -> loginFrame.dispose());

        jobPreferencesButton.addActionListener(e -> openJobPreferences(emailField.getText()));

        loginFrame.setVisible(true);
    }

    private void openJobPreferences(String jobSeekerEmail) {
        JobSeeker jobSeeker = jobSeekers.get(jobSeekerEmail);
        if (jobSeeker == null) {
            showMessage("You need to log in first!");
            return;
        }

        Frame preferencesFrame = new Frame("Matching Jobs");
        preferencesFrame.setSize(400, 400);
        preferencesFrame.setLayout(new GridLayout(0, 1));

        Label headerLabel = new Label("Matching Jobs:");
        preferencesFrame.add(headerLabel);

        List<String> seekerSkills = jobSeeker.getSkills();

        boolean matchFound = false;
        for (Employer employer : employers.values()) {
            for (Job job : employer.getJobs()) {
                for (String skill : seekerSkills) {
                    if (job.getRequiredSkills().contains(skill)) {
                        Label jobLabel = new Label("Company name:"+employer.getCompanyName() + " Offering job:" + job.getTitle());
                        preferencesFrame.add(jobLabel);
                        matchFound = true;
                        break;
                    }
                }
            }
        }

        if (!matchFound) {
            preferencesFrame.add(new Label("No matching jobs found."));
        }

        Button closeButton = new Button("Close");
        closeButton.addActionListener(e -> preferencesFrame.dispose());
        preferencesFrame.add(closeButton);

        preferencesFrame.setVisible(true);
    }

    private void openEmployerMenu() {
        Frame employerMenu = new Frame("Employer Menu");
        employerMenu.setSize(400, 300);
        employerMenu.setLayout(new GridLayout(5, 1));

        Label employerLabel = new Label("Employer Menu", Label.CENTER);
        employerMenu.add(employerLabel);

        Button registerButton = new Button("Register");
        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        employerMenu.add(registerButton);
        employerMenu.add(loginButton);
        employerMenu.add(backButton);

        // Button Actions
        registerButton.addActionListener(e -> openEmployerRegistration());
        loginButton.addActionListener(e -> openEmployerLogin());
        backButton.addActionListener(e -> employerMenu.dispose());

        employerMenu.setVisible(true);
    }

    private void openEmployerRegistration() {
        Frame registrationFrame = new Frame("Employer Registration");
        registrationFrame.setSize(400, 300);
        registrationFrame.setLayout(new GridLayout(5, 2));

        Label companyNameLabel = new Label("Company Name:");
        TextField companyNameField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();

        Button registerButton = new Button("Register");
        Button cancelButton = new Button("Cancel");

        registrationFrame.add(companyNameLabel);
        registrationFrame.add(companyNameField);
        registrationFrame.add(emailLabel);
        registrationFrame.add(emailField);
        registrationFrame.add(passwordLabel);
        registrationFrame.add(passwordField);
        registrationFrame.add(registerButton);
        registrationFrame.add(cancelButton);

        // Button Actions
        registerButton.addActionListener(e -> {
            String companyName = companyNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            Employer employer = new Employer(companyName, email, password);
            employers.put(email, employer);

            showMessage("Employer registered successfully!");
            registrationFrame.dispose();
        });

        cancelButton.addActionListener(e -> registrationFrame.dispose());

        registrationFrame.setVisible(true);
    }

    private void openEmployerLogin() {
        Frame loginFrame = new Frame("Employer Login");
        loginFrame.setSize(400, 300);
        loginFrame.setLayout(new GridLayout(4, 2));

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();

        Button loginButton = new Button("Login");
        Button postJobButton = new Button("Post Job");
        Button cancelButton = new Button("Cancel");

        loginFrame.add(emailLabel);
        loginFrame.add(emailField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.add(postJobButton);
        loginFrame.add(cancelButton);

        postJobButton.setEnabled(false);

        // Button Actions
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (employers.containsKey(email) && employers.get(email).getPassword().equals(password)) {
                showMessage("Employer login successful!");
                postJobButton.setEnabled(true);
            } else {
                showMessage("Invalid email or password. Please try again.");
            }
        });

        cancelButton.addActionListener(e -> loginFrame.dispose());

        postJobButton.addActionListener(e -> openPostJob(emailField.getText()));

        loginFrame.setVisible(true);
    }

    private void openPostJob(String employerEmail) {
        Employer employer = employers.get(employerEmail);
        if (employer == null) {
            showMessage("You need to log in first!");
            return;
        }

        Frame postJobFrame = new Frame("Post a Job");
        postJobFrame.setSize(400, 400);
        postJobFrame.setLayout(new GridLayout(4, 2));

        Label jobTitleLabel = new Label("Job Title:");
        TextField jobTitleField = new TextField();
        Label skillsLabel = new Label("Required Skills (comma-separated):");
        TextField skillsField = new TextField();

        Button postButton = new Button("Post Job");
        Button cancelButton = new Button("Cancel");

        postJobFrame.add(jobTitleLabel);
        postJobFrame.add(jobTitleField);
        postJobFrame.add(skillsLabel);
        postJobFrame.add(skillsField);
        postJobFrame.add(postButton);
        postJobFrame.add(cancelButton);

        // Button Actions
        postButton.addActionListener(e -> {
            String jobTitle = jobTitleField.getText();
            String skillsStr = skillsField.getText();

            List<String> skills = new ArrayList<>();
            for (String skill : skillsStr.split(",")) {
                skills.add(skill.trim());
            }

            Job job = new Job(jobTitle, skills);
            employer.addJob(job);

            showMessage("Job posted successfully!");
            postJobFrame.dispose();
        });

        cancelButton.addActionListener(e -> postJobFrame.dispose());

        postJobFrame.setVisible(true);
    }

    private void showMessage(String message) {
        Dialog dialog = new Dialog(this, "Message", true);
        dialog.setSize(300, 100);
        dialog.setLayout(new FlowLayout());
        dialog.add(new Label(message));
        Button okButton = new Button("OK");
        okButton.addActionListener(e -> dialog.dispose());
        dialog.add(okButton);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new OnlineJobs();
    }
}

class JobSeeker {
    private String name;
    private String email;
    private String password;
    private List<String> skills;

    public JobSeeker(String name, String email, String password, List<String> skills) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.skills = skills;
    }

    public List<String> getSkills() {
        return skills;
    }

    public String getPassword() {
        return password;
    }
}

class Employer {
    private String companyName;
    private String email;
    private String password;
    private List<Job> jobs;

    public Employer(String companyName, String email, String password) {
        this.companyName = companyName;
        this.email = email;
        this.password = password;
        this.jobs = new ArrayList<>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPassword() {
        return password;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void addJob(Job job) {
        jobs.add(job);
    }
}

class Job {
    private String title;
    private List<String> requiredSkills;

    public Job(String title, List<String> requiredSkills) {
        this.title = title;
        this.requiredSkills = requiredSkills;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }
}