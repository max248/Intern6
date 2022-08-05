package com.example.intern6;

import com.example.intern6.model.Users;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
public class GeneratorService {
    private final String LATIN = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final String CYRILLIC = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    public List<Users> generateUser(String country, double error, int seed) {

        Random random = new Random(seed);

        List<Users> users = new ArrayList<>();

        Faker faker = new Faker(new Locale(country), random);
        for (int i = 1; i <= 20; i++) {
            Users user = new Users();
            user.setFakeId((long) faker.number().numberBetween(100,999999));
            user.setFullName(faker.name().fullName());
            user.setAddress(generateAddress(country, faker));
            user.setPhoneNumber(generatePhoneNumber(country, faker));
            user.setId((long) i);
            users.add(user);
        }
        if (error > 0)
            generateDataWithError(users, error, seed, country);

        return users;
    }

    private String generateAddress(String country, Faker faker) {
        return country.equals("ru") ? faker.address().fullAddress().replaceFirst("###### ", "") : faker.address().fullAddress();
    }

    private String generatePhoneNumber(String country, Faker faker) {
        return country.equals("ru") ? faker.phoneNumber().phoneNumber() : faker.phoneNumber().cellPhone();
    }

    public List<Users> generateNext10Data(int lengthOfData, String locale, double errorValue, int seedValue) {

        Random random = new Random(seedValue);

        List<Users> users = new ArrayList<>();

        Faker faker = new Faker(new Locale(locale), random);
        Users user = new Users();
        for (int i = 1; i < lengthOfData + 10; i++) {
            if (i >= lengthOfData) {
                user.setFakeId((long) faker.number().numberBetween(100,999999));
                user.setFullName(faker.name().fullName());
                user.setAddress(generateAddress(locale, faker));
                user.setPhoneNumber(generatePhoneNumber(locale, faker));
                user.setId((long) i);
                users.add(user);
            } else
                user.setFakeId((long) faker.number().numberBetween(100,999999));
                user.setFullName(faker.name().fullName());
                user.setAddress(generateAddress(locale, faker));
                user.setPhoneNumber(generatePhoneNumber(locale, faker));
                user.setId((long) i);
        }

        if (errorValue > 0)
            generateDataWithError(users, errorValue, seedValue, locale);

        return users;
    }

    private void generateDataWithError(List<Users> users, double errorValue, int seedValue, String country) {
        Random random = new Random(seedValue);
        for (int i = 0; i < errorValue; i++) {
            for (Users fakeUser : users) {
                int field = random.nextInt(3);
                int method = random.nextInt(3);
                switch (field) {
                    case 0:
                        String fullName = fakeUser.getFullName();
                        fakeUser.setFullName(method == 0 ?
                                addSymbols(fullName, false, country.equals("ru"), random) : method == 1 ?
                                changeSymbols(fullName, random) : deleteSymbols(fullName, random)
                        );
                        break;
                    case 1:
                        String address = fakeUser.getAddress();
                        fakeUser.setAddress(method == 0 ?
                                addSymbols(address, false, country.equals("ru"), random) : method == 1 ?
                                changeSymbols(address, random) : deleteSymbols(address, random)
                        );
                        break;
                    case 2:
                        String phoneNumber = fakeUser.getPhoneNumber();
                        fakeUser.setPhoneNumber(method == 0 ?
                                addSymbols(phoneNumber, true, country.equals("ru"), random) : method == 1 ?
                                changeSymbols(phoneNumber, random) : deleteSymbols(phoneNumber, random)
                        );
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private String addSymbols(String data, boolean isNumber, boolean isCyrillic, Random random) {
        int index = random.nextInt(data.length());
        if (isCyrillic) {
            return addErrorsToData(data, isNumber, index, CYRILLIC, random);
        } else {
            return addErrorsToData(data, isNumber, index, LATIN, random);
        }

    }

    private String addErrorsToData(String data, boolean isNumber, int index, String characters, Random random) {
        if (index == data.length() - 1){
            if(isNumber){
                return data.concat(String.valueOf(random.nextInt(10)));
            } else {
                return data.concat(String.valueOf(characters.charAt(random.nextInt(52))));
            }
        }
        if(isNumber){
            return data.substring(0, index).concat(String.valueOf(random.nextInt(10)).concat(data.substring(index + 1)));
        } else {
            return  data.substring(0, index).concat(String.valueOf(characters.charAt(random.nextInt(52)))) + data.substring(index + 1);
        }
    }

    private String deleteSymbols(String data, Random random) {
        int index = random.nextInt(data.length());
        return data.replace(data.charAt(index), ' ');
    }

    private String changeSymbols(String data, Random random) {
        int changeFromIndex = random.nextInt(data.length());
        int changeToIndex = random.nextInt(data.length());
        char[] chars = data.toCharArray();
        char changeFrom = chars[changeFromIndex];
        chars[changeFromIndex] = chars[changeToIndex];
        chars[changeToIndex] = changeFrom;
        StringBuilder result = new StringBuilder();
        for (char aChar : chars) {
            result.append(aChar);
        }
        return result.toString();
    }

}
