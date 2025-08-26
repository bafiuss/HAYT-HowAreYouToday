package it.unisa.HAYT.config;

import it.unisa.HAYT.entities.*;
import it.unisa.HAYT.repositories.*;
import it.unisa.HAYT.servicies.AppointmentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import  it.unisa.HAYT.entities.DiaryEntity.Mood;
import  it.unisa.HAYT.entities.DiaryEntity.Sentiment;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DatabasePopulator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private TipRepository tipRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void populate() throws IOException {
        if (userRepository.count() == 0) {

            byte[] psychotherapistImage = loadImage("psychotherapist.jpg");

            byte[] imagePatient1 = loadImage("patient-1.jpg");
            byte[] imagePatient2 = loadImage("patient-2.jpg");
            byte[] imagePatient3 = loadImage("patient-3.jpg");

            PsychotherapistEntity psychotherapist = new PsychotherapistEntity("fsessa02@gmail.com", "Fabio", "Sessa", passwordEncoder.encode("Prova123"), "Campania - 150", psychotherapistImage);

            PatientEntity patient1 = new PatientEntity("mariorossi@gmail.com", "Mario", "Rossi", passwordEncoder.encode("SecurePass1"), psychotherapist,imagePatient2);
            PatientEntity patient2 = new PatientEntity("lucaverdi@gmail.com", "Luca", "Verdi", passwordEncoder.encode("Passw0rd3"), psychotherapist, imagePatient1);
            PatientEntity patient3 = new PatientEntity("giorgiagallo@gmail.com", "Giorgia", "Gallo", passwordEncoder.encode("SafePass5"), psychotherapist, imagePatient3);

            List<UserEntity> patients = List.of(patient1, patient2, patient3);

            userRepository.save(psychotherapist);
            userRepository.saveAll(patients);

            if(messageRepository.count() == 0){

                List<MessageEntity> messages = List.of(
                        new MessageEntity(psychotherapist, patient1, "Hi Mario are u ok today?", LocalDateTime.of(2025, 4, 7, 14, 30)),
                        new MessageEntity(patient1, psychotherapist, "Not great, honestly. Been feeling overwhelmed", LocalDateTime.of(2025, 4, 7, 15, 30)),
                        new MessageEntity(psychotherapist, patient1, "I see. Can you tell me what's been causing that feeling?", LocalDateTime.of(2025, 4, 7, 16, 0)),
                        new MessageEntity(patient1, psychotherapist, "It's mostly work. I feel like I can't keep up with everything.", LocalDateTime.of(2025, 4, 7, 16, 5)),
                        new MessageEntity(psychotherapist, patient1, "That sounds tough. Have you tried any strategies to manage the workload, or is it more about how you're feeling?", LocalDateTime.of(2025, 4, 7, 17, 0)),

                        new MessageEntity(psychotherapist, patient2, "Hey Luca, how are you feeling today?", LocalDateTime.of(2025, 4, 7, 10, 0)),
                        new MessageEntity(patient2, psychotherapist, "A bit anxious, to be honest. It's been a rough morning.", LocalDateTime.of(2025, 4, 7, 10, 45)),
                        new MessageEntity(psychotherapist, patient2, "I'm sorry to hear that. Want to talk about what triggered it?", LocalDateTime.of(2025, 4, 7, 11, 15)),
                        new MessageEntity(patient2, psychotherapist, "I had an argument with my partner last night. Still processing it.", LocalDateTime.of(2025, 4, 7, 11, 20)),
                        new MessageEntity(psychotherapist, patient2, "Thank you for sharing that. Conflicts can be really draining. Do you feel like it’s affecting other parts of your day?", LocalDateTime.of(2025, 4, 7, 12, 0)),

                        new MessageEntity(psychotherapist, patient3, "Good morning Giorgia, how have you been since our last chat?", LocalDateTime.of(2025, 4, 5, 9, 15)),
                        new MessageEntity(patient3, psychotherapist, "Morning. I've been better. Struggling with motivation lately.", LocalDateTime.of(2025, 4, 5, 9, 50)),
                        new MessageEntity(psychotherapist, patient3, "Thanks for being honest. Can you think of anything that might be contributing to that?", LocalDateTime.of(2025, 4, 5, 10, 10)),
                        new MessageEntity(patient3, psychotherapist, "I think it's the change in season. The gloomy weather gets to me.", LocalDateTime.of(2025, 4, 5, 10, 15)),
                        new MessageEntity(psychotherapist, patient3, "That makes sense. Seasonal changes can definitely impact mood. Would you be open to trying a few mood-lifting techniques?", LocalDateTime.of(2025, 4, 5, 10, 45))

                );

                messageRepository.saveAll(messages);

            }

            if(questionnaireRepository.count() == 0){

                List<QuestionnaireEntity> questionnaires = List.of(
                        new QuestionnaireEntity(patient1,2,5,3,1,1,5,4,2,3,4,1,5,6,2,3,1,2,4,2,LocalDateTime.of(2025, 3, 16, 10, 45)),
                        new QuestionnaireEntity(patient1, 3, 4, 2, 5, 4, 3, 2, 5, 4, 1, 3, 4, 5, 2, 3, 1, 4, 2, 1, LocalDateTime.of(2025, 3, 23, 10, 45)),
                        new QuestionnaireEntity(patient1, 4, 3, 5, 2, 4, 4, 1, 5, 3, 2, 4, 5, 3, 1, 5, 2, 5, 5, 4, LocalDateTime.of(2025, 3, 30, 10, 45)),
                        new QuestionnaireEntity(patient1, 5, 2, 4, 1, 3, 5, 2, 4, 3, 1, 5, 4, 2, 3, 4, 1, 4, 2, 1,LocalDateTime.of(2025, 4, 6, 10, 45)),
                        new QuestionnaireEntity(patient2, 3, 5, 2, 4, 4, 3, 1, 3, 5, 2, 4, 5, 2, 3, 4, 1, 1, 2, 4, LocalDateTime.of(2025, 3, 10, 10, 45)),
                        new QuestionnaireEntity(patient2, 2, 4, 5, 3, 2, 4, 3, 5, 2, 1, 4, 5, 3, 2, 4, 1, 2, 1, 5, LocalDateTime.of(2025, 3, 17, 10, 45))

                );

                questionnaireRepository.saveAll(questionnaires);

            }

            if(appointmentRepository.count() == 0){

                List<AppointmentEntity> appointments = List.of(
                        new AppointmentEntity("Initial Consultation", LocalDateTime.of(2025, 3, 28, 9, 0), "I need to talk about some struggles I'm facing and see if therapy can help me.", patient1, psychotherapist),
                        new AppointmentEntity("Progress Check", LocalDateTime.of(2025, 3, 31, 14, 30), "I'd like to see how things are going and whether there have been any improvements.", patient1, psychotherapist),
                        new AppointmentEntity("Stress Management", LocalDateTime.of(2025, 4, 1, 11, 0), "I'm dealing with anxiety, and I'd like to discuss better ways to manage it.", patient1, psychotherapist),
                        new AppointmentEntity("Reflections on the Journey", LocalDateTime.of(2025, 4, 5, 16, 0), "I want to reflect on how far I've come and see if the therapy is working for me.", patient1, psychotherapist),
                        new AppointmentEntity("Emotional Support Session", LocalDateTime.of(2025, 4, 7, 10, 30), "I'm going through some tough times, and I need support to keep going.", patient1, psychotherapist),

                        new AppointmentEntity("First Session", LocalDateTime.of(2025, 4, 3, 10, 0), "I want to start therapy and talk about my recent struggles.", patient2, psychotherapist),
                        new AppointmentEntity("Check-up Session", LocalDateTime.of(2025, 4, 7, 13, 30), "I feel like I need a session to talk about my progress and my feelings.", patient2, psychotherapist),
                        new AppointmentEntity("Coping Strategies", LocalDateTime.of(2025, 4, 9, 15, 0), "I'd like to focus on learning strategies to cope with stress and emotional challenges.", patient2, psychotherapist),
                        new AppointmentEntity("Therapy Review", LocalDateTime.of(2025, 4, 15, 9, 30), "I want to assess how the therapy is going and see if I can manage things better.", patient2, psychotherapist),
                        new AppointmentEntity("Mental Health Check-in", LocalDateTime.of(2025, 4, 20, 11, 0), "I'm feeling overwhelmed lately and need to talk to someone about it.", patient2, psychotherapist),

                        new AppointmentEntity("Therapy Start", LocalDateTime.of(2025, 4, 2, 14, 30), "I’ve been going through some personal issues and need to start therapy to work through them.", patient3, psychotherapist),
                        new AppointmentEntity("Mid-Therapy Reflection", LocalDateTime.of(2025, 4, 6, 11, 30), "I want to talk about how I’m feeling halfway through my therapy journey.", patient3, psychotherapist),
                        new AppointmentEntity("Addressing Anxiety", LocalDateTime.of(2025, 4, 8, 10, 0), "I’ve been struggling with anxiety, and I need some guidance on how to manage it better.", patient3, psychotherapist),
                        new AppointmentEntity("Emotional Clarity", LocalDateTime.of(2025, 4, 14, 16, 0), "I feel like I need more clarity on my emotions and how they affect my day-to-day life.", patient3, psychotherapist),
                        new AppointmentEntity("Support and Feedback", LocalDateTime.of(2025, 4, 18, 9, 0), "I’m looking for support and feedback on how to handle my challenges more effectively.", patient3, psychotherapist)
                );

                appointmentRepository.saveAll(appointments);
            }

            if(diaryRepository.count() == 0){
                List<DiaryEntity> diaryEntries = List.of(
                        new DiaryEntity("Today was overwhelming. I felt like I couldn’t handle anything.", LocalDateTime.of(2025, 7, 5, 10, 0), Mood.grief, Sentiment.negative, "Too Much to Handle", patient1),
                        new DiaryEntity("I woke up feeling energized and went for a long walk.", LocalDateTime.of(2025, 7, 6, 9, 30), Mood.optimism, Sentiment.positive, "A Productive Morning", patient2),
                        new DiaryEntity("I can’t stop thinking about all the mistakes I’ve made.", LocalDateTime.of(2025, 7, 7, 21, 15), Mood.remorse, Sentiment.negative, "Obsessive Thoughts", patient3),
                        new DiaryEntity("I’m grateful for the support I received from my therapist today.", LocalDateTime.of(2025, 7, 8, 14, 50), Mood.gratitude, Sentiment.positive, "Support Matters", patient1),
                        new DiaryEntity("I feel so alone, like no one really understands me.", LocalDateTime.of(2025, 7, 9, 19, 40), Mood.grief, Sentiment.negative, "Crushing Loneliness", patient2),
                        new DiaryEntity("Today I managed to open up about how I really feel.", LocalDateTime.of(2025, 7, 10, 16, 10), Mood.relief, Sentiment.positive, "Opening Up", patient3),
                        new DiaryEntity("I don't even know what I'm feeling anymore. Everything’s blurry.", LocalDateTime.of(2025, 7, 11, 12, 25), Mood.confusion, Sentiment.neutral, "Mental Fog", patient1),
                        new DiaryEntity("I’m really angry at myself for not sticking to my plans.", LocalDateTime.of(2025, 7, 12, 18, 0), Mood.anger, Sentiment.negative, "Frustrated with Myself", patient2),
                        new DiaryEntity("The day went by without much thought. It was just okay.", LocalDateTime.of(2025, 7, 13, 15, 45), Mood.neutral, Sentiment.neutral, "Just Another Day", patient3),
                        new DiaryEntity("I finally felt a spark of happiness while doing something I love.", LocalDateTime.of(2025, 7, 14, 11, 20), Mood.joy, Sentiment.positive, "Joy in Small Things", patient1),
                        new DiaryEntity("The day started off well but ended in deep sadness.", LocalDateTime.of(2025, 7, 15, 17, 35), Mood.sadness, Sentiment.negative, "Emotional Rollercoaster", patient2),
                        new DiaryEntity("Even though it was hard, I feel like I grew from today’s experience.", LocalDateTime.of(2025, 7, 16, 20, 5), Mood.realization, Sentiment.positive, "Growing Through Struggles", patient3),
                        new DiaryEntity("My mind is so noisy at night, I can’t seem to quiet it.", LocalDateTime.of(2025, 7, 17, 22, 10), Mood.nervousness, Sentiment.negative, "Noisy Thoughts", patient1),
                        new DiaryEntity("Despite everything, I felt lucky to have my family today.", LocalDateTime.of(2025, 7, 18, 13, 55), Mood.gratitude, Sentiment.positive, "Thankful for My Family", patient2),
                        new DiaryEntity("I cried for no clear reason today. Maybe I needed it.", LocalDateTime.of(2025, 7, 19, 8, 45), Mood.sadness, Sentiment.negative, "Unexplained Tears", patient3)
                );


                diaryRepository.saveAll(diaryEntries);
            }

            if (tipRepository.count() == 0) {
                List<TipEntity> tips = List.of(
                        new TipEntity("breathing_exercise", "Box Breathing",
                                "Sit upright in a quiet space. Inhale slowly through your nose for a count of 4, filling your lungs completely.",
                                "Hold your breath for a count of 4, keeping your body relaxed and still during the pause.",
                                "Exhale slowly through your mouth for a count of 4, emptying your lungs fully without straining.",
                                "Hold your breath again for a count of 4 before beginning the next cycle. Repeat for several rounds.",
                                "none"),

                        new TipEntity("breathing_exercise", "4-7-8 Breathing",
                                "Sit or lie down comfortably. Inhale deeply through your nose for a count of 4, letting your lungs expand fully.",
                                "Hold your breath for a count of 7. Keep your body relaxed and your focus on the stillness of the pause.",
                                "Exhale slowly through your mouth for a count of 8, releasing all the air and tension in your body.",
                                "Repeat this cycle for 4 full rounds, allowing your mind and body to settle into a relaxed state.",
                                "none"),

                        new TipEntity("breathing_exercise", "Diaphragmatic Breathing",
                                "Lie on your back or sit with a straight spine. Place one hand on your chest and the other on your belly.",
                                "Inhale deeply through your nose, aiming to expand your belly rather than your chest. Feel your lower hand rise.",
                                "Exhale slowly through your mouth, tightening your abdominal muscles to push the air out fully.",
                                "Continue this deep belly breathing for 5–10 minutes, focusing on the rise and fall of your abdomen.",
                                "none"),

                        new TipEntity("breathing_exercise", "Alternate Nostril Breathing",
                                "Sit comfortably with your spine straight. Use your thumb to gently close your right nostril.",
                                "Inhale deeply through the left nostril, then close it with your ring finger and release your thumb.",
                                "Exhale through the right nostril, then inhale through it. Close it, release the left nostril, and exhale left.",
                                "This completes one cycle. Continue alternating for 5–10 cycles, keeping your breath smooth and steady.",
                                "none"),

                        new TipEntity("muscle_relaxation", "Progressive Muscle Relaxation",
                                "Sit or lie down comfortably. Start by focusing on your feet and intentionally tense the muscles tightly for 5 seconds.",
                                "Slowly release the tension while exhaling, noticing the contrast between tightness and relaxation.",
                                "Move upward through your body—legs, abdomen, chest, arms, face—tensing each group, then releasing slowly.",
                                "Maintain awareness of the physical sensations as you relax each part, letting go of any lingering tension.",
                                "none"),

                        new TipEntity("muscle_relaxation", "Facial Muscle Relaxation",
                                "Sit or lie down comfortably. Begin by raising your eyebrows as high as possible and hold for 5 seconds.",
                                "Release the tension while exhaling, noticing the softening of your forehead and temples.",
                                "Tightly squint your eyes and clench your jaw for 5 seconds, then relax those areas completely.",
                                "Repeat the process once more, focusing on the pleasant sensation of letting go.",
                                "none"),

                        new TipEntity("mindfulness_meditation", "Body Scan Meditation",
                                "Lie down comfortably, close your eyes, and settle into the present moment with awareness of your breath.",
                                "Systematically move your attention through the body—starting from the toes of the left foot, moving up the leg, then the right side, torso, arms, neck, face, and head.",
                                "With each region, breathe into and out of it, noticing sensations without judgment and letting go as you move on.",
                                "After scanning the whole body, rest in stillness, then gently move your hands and feet, slowly returning to the room.",
                                "none"),

                        new TipEntity("mindfulness_meditation", "Walking Meditation",
                                "Choose a quiet space to walk slowly and mindfully. Keep your gaze soft and your steps natural.",
                                "Pay close attention to each step: how your heel touches the ground, how your weight shifts, and how you lift your foot.",
                                "Use your breath as an anchor—inhale as you lift your foot, exhale as you place it down. Remember, you are not trying to get anywhere—just be present with each step.",
                                "If your mind wanders, gently return to the sensation of walking and breathing.",
                                "Practice for at least 10 minutes, letting each step bring you into the present moment."),

                        new TipEntity("mindfulness_meditation", "Sitting Meditation",
                                "Sit in a comfortable position with your back straight and bring full attention to your breath as it flows in and out.",
                                "Notice when your mind wanders away from the breath, without judgment or frustration.",
                                "Each time, gently bring your attention back to the breath, focusing on the rising and falling of the belly.",
                                "Through this practice, cultivate concentration, patience, stability, and non-judgmental awareness.",
                                "none"),


                        new TipEntity("mindfulness_meditation", "5-4-3-2-1 Grounding Technique",
                                "Start by identifying 5 things you can see around you. Observe colors, shapes, and textures with full attention.",
                                "Now find 4 things you can physically feel—your clothes, the chair, your feet on the floor—focus on these sensations.",
                                "Next, identify 3 things you can hear. Listen to distant and close sounds, without labeling them.",
                                "Notice 2 things you can smell. If nothing is obvious, gently inhale and see what you detect.",
                                "Finally, bring attention to 1 thing you can taste, even if it’s just the neutral taste in your mouth.")
                );

                tipRepository.saveAll(tips);
            }



        }


    }

    private byte[] loadImage(String imagePath) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/images/profile-images/" + imagePath);
        return FileCopyUtils.copyToByteArray(resource.getInputStream());
    }


}
