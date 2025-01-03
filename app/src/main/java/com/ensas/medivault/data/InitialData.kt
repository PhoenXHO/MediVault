package com.ensas.medivault.data

import com.ensas.medivault.data.model.Medication

object InitialData {
    val medications = listOf(
        Medication(
            name = "Nofebril - Paracetamol",
            contents = "500 mg, 20 tablets",
            price = 6.50,
            description = """
                **Nofebril** is a pain reliever and a fever reducer. It is used to treat many conditions such as headache, muscle aches, arthritis, backache, toothaches, colds, and fevers. It relieves pain in mild arthritis but has no effect on the underlying inflammation and swelling of the joint.
                
                It is also used to relieve mild to moderate pain from a headache, toothache, cold, flu, joint pain, or periods. It is also used to reduce fever and to relieve minor aches and pain due to the common cold or flu. Paracetamol is a painkiller for mild to moderate pain.
                
                Nofebril is a common pain reliever that is used to treat mild to moderate pain from headaches, muscle aches, menstrual periods, colds, sore throats, toothaches, backaches, and fevers. It is also used to relieve pain from mild arthritis.
            """.trimIndent(),
            importantInfo = """
                **Do not use if you are allergic to paracetamol or any of the other ingredients in this medicine.**
                
                **Do not take more than the recommended dose, as this can be harmful, including serious harm to your liver.**
                
                **Do not take if you are already taking other medicines containing paracetamol or other pain relievers/fever reducers.**
            """.trimIndent(),
            precautions = """
                Do not use if you are allergic to paracetamol or any of the other ingredients in this medicine. Make sure to consult a doctor before use if you have liver or kidney disease, if you are taking other medications, or if you are pregnant or breastfeeding.
            """.trimIndent(),
            uses = """
                Nofebril is available over the counter (OTC) and as also as a prescription medicine. It is used to treat many conditions such as:
                - Headache.
                - Muscle aches.
                - Mild arthritis.
                - Backache.
                - Toothaches.
                - Colds and fevers.
                - Sore throats.
            """.trimIndent(),
            sideEffects = """
                **Common side effects:**
                - Allergic reactions such as skin rash, itching, or hives.
                - Swelling of the face, lips, or tongue which may cause difficulty in swallowing or breathing.
                - Shortness of breath, wheezing, or trouble breathing.
                - Unexplained fever with severe muscle stiffness, sweating, or a fast heart rate.
                - Severe blistering, peeling, and red skin rash.
                
                **Rare side effects:**
                - Blood in the urine.
                - Unexplained bruising or bleeding.
                - Nausea, vomiting, loss of appetite, yellowing of the skin or eyes, dark urine, pale stools, tiredness, or abdominal pain.
                - Skin rash, redness, or itching.
                - Swelling of the face, lips, mouth, throat, or neck which may cause difficulty in swallowing or breathing.
                - Shortness of breath, wheezing, or trouble breathing.
                - Unexplained fever with severe muscle stiffness, sweating, or a fast heart rate.
                - Severe blistering, peeling, and red skin rash.
            """.trimIndent(),
            dosage = """
                **Adults and children over 12 years of age:**
                - Take 1 to 2 tablets every 4 to 6 hours as needed.
                - Do not take more than 8 tablets in 24 hours.
                
                **Children 6 to 12 years of age:**
                - Take 1/2 to 1 tablet every 4 to 6 hours as needed.
                - Do not take more than 4 tablets in 24 hours.
                
                **Children under 6 years of age:**
                - Consult a doctor before use.
            """.trimIndent(),
            imageUrl = "https://e-xportmorocco.com/storage/produits/1640809486.jpeg"
        ),
        Medication(
            name = "Tofranil - Imipramine",
            contents = "25 mg, 100 tablets",
            price = 53.40,
            description = """
                **Tofranil** is a tricyclic antidepressant used to treat depression. It is also used on a short-term basis to treat bed-wetting in children aged 6 and older. It works by increasing the levels of certain chemicals in the brain that help elevate mood.
                
                Tofranil is used to treat depression. It is also used on a short-term basis to treat bed-wetting in children aged 6 and older. It works by increasing the levels of certain chemicals in the brain that help elevate mood.
            """.trimIndent(),
            uses = """
                Tofranil is used to treat depression. It is also used on a short-term basis to treat bed-wetting in children aged 6 and older.
                
                Tofranil may also be used for purposes not listed in this medication guide.
            """.trimIndent(),
            importantInfo = """
                Stay alert to changes in your mood or symptoms. Report any new or worsening symptoms to your doctor. Avoid driving or hazardous activity until you know how this medicine will affect you. Avoid getting up too fast from a sitting or lying position, or you may feel dizzy.
            """.trimIndent(),
            precautions = """
                Do not use Tofranil if you have used an MAO inhibitor in the past 14 days. A dangerous drug interaction could occur. MAO inhibitors include isocarboxazid, linezolid, methylene blue injection, phenelzine, rasagiline, selegiline, tranylcypromine, and others.
                
                You should not take Tofranil if you are allergic to imipramine or if you have recently had a heart attack. Do not use this medicine if you have used an MAO inhibitor in the past 14 days. A dangerous drug interaction could occur. MAO inhibitors include isocarboxazid, linezolid, methylene blue injection, phenelzine, rasagiline, selegiline, tranylcypromine, and others.
            """.trimIndent(),
            sideEffects = """
                **Common side effects:**
                - Drowsiness.
                - Dizziness.
                - Dry mouth.
                - Blurred vision.
                - Constipation.
                
                **Serious side effects:**
                - Fast or pounding heartbeats.
                - Sudden shortness of breath.
                - Confusion, hallucinations.
                - Unusual thoughts or behavior.
                - Seizure (convulsions).
                - Easy bruising or bleeding.
                - Sudden weakness or ill feeling, fever, chills, sore throat, mouth sores, red or swollen gums.
            """.trimIndent(),
            dosage = """
                **Adults:**
                - The usual dose is 75 mg to 150 mg per day.
                - The dose may be increased to 200 mg per day if needed.
                
                **Children:**
                - The usual dose is 25 mg to 50 mg per day.
                - The dose may be increased to 100 mg per day if needed.
            """.trimIndent(),
            imageUrl = "https://assospharma.com/wp-content/uploads/2021/02/tofranil.jpg"
        ),
        Medication(
            name = "Valium",
            contents = "10 mg, 30 tablets",
            price = 22.70,
            description = """
                **Valium** is a benzodiazepine *(ben-zoe-dye-AZE-eh-peens)*. It is used to treat anxiety disorders, alcohol withdrawal symptoms, or muscle spasms. It is sometimes used with other medications to treat seizures.
            """.trimIndent(),
            uses = """
                Valium is used to treat anxiety disorders, alcohol withdrawal symptoms, or muscle spasms. It is sometimes used with other medications to treat seizures.
                
                Valium may also be used for purposes not listed in this medication guide.
            """.trimIndent(),
            importantInfo = """
                You should not use Valium if you are allergic to diazepam or similar medicines (Klonopin, Xanax, and others), or if you have myasthenia gravis, severe liver disease, narrow-angle glaucoma, a severe breathing problem, or sleep apnea.
                
                Misuse of habit-forming medicine can cause addiction, overdose, or death. Selling or giving away this medicine is against the law.
            """.trimIndent(),
            precautions = """
                You should not use Valium if you are allergic to diazepam or similar medicines (Klonopin, Xanax, and others), or if you have myasthenia gravis, severe liver disease, narrow-angle glaucoma, a severe breathing problem, or sleep apnea.
                
                **Never share this medicine with another person, especially someone with a history of drug abuse or addiction. MISUSE OF THIS MEDICINE CAN CAUSE ADDICTION, OVERDOSE, OR DEATH.**
                
                Selling or giving away this medicine is against the law.
            """.trimIndent(),
            sideEffects = """
                **Common side effects:**
                - Drowsiness.
                - Tired feeling.
                - Muscle weakness.
                - Loss of coordination.
                - Memory problems.
                
                **Serious side effects:**
                - Weak or shallow breathing.
                - Severe drowsiness or dizziness.
                - Unusual changes in mood or behavior.
                
                Drowsiness or dizziness may last longer in older adults. Use caution to avoid falling or accidental injury.
            """.trimIndent(),
            dosage = """
                **Adults:**
                - The usual dose is 2 mg to 10 mg two to four times a day.
                
                **Children:**
                - The usual dose is 1 mg to 2.5 mg three to four times a day.
            """.trimIndent(),
            imageUrl = "https://images.sunstore.ch/product-images/valium-cpr-10-mg-100-pce-main-00016H.jpg"
        ),
        Medication(
            name = "Fabrazyme",
            contents = "35 mg, 1 vial",
            price = 35486.00,
            description = """
                **Fabrazyme** is used to treat Fabry disease, a rare genetic disorder. It works by replacing the missing enzyme in the body.
                
                Fabrazyme is a man-made form of the naturally-occurring alpha-galactosidase A enzyme. A deficiency of this enzyme is called Fabry disease. Agalsidase beta reduces deposits of globotriaosylceramide (GL-3) in the kidneys and certain other cells in the body.

                Fabrazyme is used in the treatment of Fabry disease (a deficiency of alpha-galactosidase A enzyme) in adults and children at least 2 years old.

                Fabrazyme may also be used for purposes other than those listed here.
            """.trimIndent(),
            uses = """
                Fabrazyme is used to treat Fabry disease, a rare genetic disorder. It works by replacing the missing enzyme in the body.
                
                Fabrazyme may also be used for purposes not listed in this medication guide.
            """.trimIndent(),
            importantInfo = """
                **Many people have strong allergic reactions to Fabrazyme during the infusion.** You will be watched closely during your treatment to make sure you do not have an allergic reaction.
                
                Make sure to tell your caregivers if you feel dizzy, nauseated, light-headed, sweaty, or have a headache, chest pain, trouble breathing, or fast or slow heartbeats.
                
                Most patients treated with Fabrazyme develop antibodies to agalsidase beta and many will develop symptoms of an infusion reaction. A slow rate of injection of the medication and pretreatment with other medications may decrease the severity of these symptoms. Emergency medical attention may be required if a severe allergic reaction is experienced.
            """.trimIndent(),
            precautions = """
                To make sure Fabrazyme is safe for you, tell your doctor if you have ever had:
                - Heart problems.
                - Breathing problems.
                - A stroke or heart attack.
                - High blood pressure.
                
                Tell your doctor if you are pregnant or breastfeeding.
            """.trimIndent(),
            sideEffects = """
                Get emergency medical help if you have signs of an allergic reaction to Fabrazyme:
                - Wheezing or trouble breathing.
                - Swelling of your face, lips, tongue, or throat.
                - Hives, rash, or itching.
                
                Some side effects may occur during the injection. Tell your caregiver right away if you have any of these symptoms:
                - Chest pain or tightness.
                - Fast, slow, or uneven heartbeats.
                - Dizziness, lightheadedness, or fainting.
                - Severe headache.
                
                Common side effects may include:
                - Headache.
                - Dizziness.
                - Nausea.
                - Vomiting.
                - Fever.
                
                This is not a complete list of side effects and others may occur. Call your doctor for medical advice about side effects.
            """.trimIndent(),
            dosage = """
                **Adults:**
                - The usual dose is 1 mg/kg of body weight given every 2 weeks.
                
                **Children:**
                - The usual dose is 1 mg/kg of body weight given every 2 weeks.
                
                The initial infusion rate should not exceed 0.25 mg/min (15 mg/hr) for the first 30 minutes for all patients. If the 0.25 mg/min (15 mg/hr) infusion rate is well tolerated, the rate may be increased to 0.5 mg/min (30 mg/hr) for the next 30 minutes. If the 0.5 mg/min (30 mg/hr) infusion rate is well tolerated, the rate may be increased to 1 mg/min (60 mg/hr) for the remainder of the infusion.
            """.trimIndent(),
            imageUrl = "https://www.sansfro.com/wp-content/uploads/2023/10/Untitled-design-13-min.png"
        ),
        Medication(
            name = "Efavir - Efavirenz",
            contents = "600 mg, 30 tablets",
            price = 136.10,
            description = """
                **Efavir** is an antiviral medicine that prevents human immunodeficiency virus (HIV) from multiplying in your body. It is used to treat HIV, the virus that can cause acquired immunodeficiency syndrome (AIDS).
                
                Efavir is used to treat HIV, the virus that can cause acquired immunodeficiency syndrome (AIDS). Efavir is not a cure for HIV or AIDS.
            """.trimIndent(),
            uses = """
                Efavir is used to treat HIV, the virus that can cause acquired immunodeficiency syndrome (AIDS).
                
                Efavir may also be used for purposes not listed in this medication guide.
            """.trimIndent(),
            importantInfo = """
                You should not use Efavir if you are allergic to efavirenz or any other ingredients in the medicine. Do not take Efavir with Atripla, Complera, Odefsey, Stribild, or Symfi.
            """.trimIndent(),
            precautions = """
                **Do not use Efavir if you are pregnant.** It could harm the unborn baby. Use two forms of birth control, including a barrier form (such as a condom or diaphragm with spermicide gel) while you are taking Efavir, and for at least 12 weeks after your treatment ends.
                
                **Do not breastfeed while using Efavir.** Even if your baby is born without HIV, the virus may be passed to the baby in your breast milk.
            """.trimIndent(),
            sideEffects = """
                **Efavir may cause a serious condition called lactic acidosis.** Get emergency medical help if you have even mild symptoms such as:
                - Seizures, hallucinations, trouble sleeping, or unusual behavior.
                - Severe dizziness, fainting, fast or pounding heartbeats.
                - Severe pain in your upper stomach spreading to your back, nausea and vomiting.
                - Sudden numbness or weakness, problems with vision, speech, or balance.
            """.trimIndent(),
            dosage = """
                Follow all directions on your prescription label and read all medication guides or instruction sheets. Use the medicine exactly as directed.
                
                **Efavir must be given in combination with other antiviral medications and it should not be used alone.** Your disease may become resistant to Efavir if you do not take it in combination with other antiviral medicines your doctor has prescribed.
                
                Take Efavir on an empty stomach, preferably at bedtime.
                
                Read the medication guide or patient instructions provided with each medication in your combination therapy. Do not change your doses or medication schedule without your doctor's advice.
                
                **Swallow the tablet whole** and do not crush, chew, or break it.
                
                If you cannot swallow a tablet whole, open the tablet and sprinkle the medicine into a spoonful of applesauce. Swallow the mixture right away without chewing. Do not save it for later use.
                
                **Efavir doses are based on weight in children.** Your child's dose needs may change if the child gains or loses weight.
            """.trimIndent(),
            imageUrl = "https://5.imimg.com/data5/SELLER/Default/2024/10/461021360/GW/PY/MU/188791916/efavir-efavirenz-tablets.jpeg"
        ),
        Medication(
            name = "Codoliprane",
            contents = "500 mg, 16 tablets",
            price = 20.80,
            description = """
                **Codoliprane** is a combination medicine used to relieve moderate to severe pain. It contains paracetamol and codeine. Paracetamol is a pain reliever and a fever reducer. Codeine is an opioid pain medication.
                
                Codoliprane is available over the counter (OTC) and as a prescription medicine.
            """.trimIndent(),
            uses = """
                Codoliprane is used to relieve moderate. It is used to treat many conditions such as:
                - Headache.
                - Muscle aches.
                - Mild arthritis.
                - Backache.
                - Toothaches.
                - Colds and fevers.
                - Sore throats.
            """.trimIndent(),
            importantInfo = """
                **Do not use Codoliprane if you have used an MAO inhibitor in the past 14 days.** A dangerous drug interaction could occur. MAO inhibitors include isocarboxazid, linezolid, methylene blue injection, phenelzine, rasagiline, selegiline, tranylcypromine, and others.
                
                **Do not take more than the recommended dose, as this can be harmful, including serious harm to your liver.**
                
                **Do not take if you are already taking other medicines containing paracetamol or other pain relievers/fever reducers.**
            """.trimIndent(),
            precautions = """
                Do not use Codoliprane if you have used an MAO inhibitor in the past 14 days. Make sure to consult a doctor before use if you have liver or kidney disease, if you are taking other medications, or if you are pregnant or breastfeeding.
            """.trimIndent(),
            sideEffects = """
                **Common side effects:**
                - Allergic reactions such as skin rash, itching, or hives.
                - Swelling of the face, lips, or tongue which may cause difficulty in swallowing or breathing.
                - Shortness of breath, wheezing, or trouble breathing.
                - Unexplained fever with severe muscle stiffness, sweating, or a fast heart rate.
                - Severe blistering, peeling, and red skin rash.
                
                **Rare side effects:**
                - Blood in the urine.
                - Unexplained bruising or bleeding.
                - Nausea, vomiting, loss of appetite, yellowing of the skin or eyes, dark urine, pale stools, tiredness, or abdominal pain.
                - Skin rash, redness, or itching.
                - Swelling of the face, lips, mouth, throat, or neck which may cause difficulty in swallowing or breathing.
                - Shortness of breath, wheezing, or trouble breathing.
                - Unexplained fever with severe muscle stiffness, sweating, or a fast heart rate.
                - Severe blistering, peeling, and red skin rash.
            """.trimIndent(),
            dosage = """
                **Adults and children over 12 years of age:**
                - Take 1 to 2 tablets every 4 to 6 hours as needed.
                - Do not take more than 8 tablets in 24 hours.
                
                **Children 6 to 12 years of age:**
                - Take 1/2 to 1 tablet every 4 to 6 hours as needed.
                - Do not take more than 4 tablets in 24 hours.
                
                **Children under 6 years of age:**
                - Consult a doctor before use.
            """.trimIndent(),
            imageUrl = "https://media.pharmaciedesdrakkars.com/media/images/products/34009332207542-ld-0023-front-609bce5dea22e.jpg"
        ),
    )
}