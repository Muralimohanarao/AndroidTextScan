package com.murali.textscan;

import java.util.ArrayList;


enum CreditCardType {
    AmericanExpress, Dankort, DinersClub, Discover, JCB, Maestro, MasterCard, UnionPay, VisaElectron, Visa, NotRecognized
}



public class CCValidator {

    class CreditCardSpec {
        CreditCardPrefix[] prefixes;
        int[] lengths;

        CreditCardSpec(CreditCardPrefix[] prefixes, int[] lengths) {
            this.prefixes = prefixes;
            this.lengths = lengths;
        }
    }



    // Array of prefixes describing credit card type. E.g. Visa prefix is just number `4`
    // and the prefix is of length 1 (i.e. we check only first digit when
    // looking for prefix)
    CreditCardPrefix[] prefixes;

    /// Array of possible lengths of a credit card type. E.g. Visa can have 13,
    /// 16 or 19 digits, whereas MasterCard can be only 16 digits.
    int[] lengths;


    /// Initialize new CCValidator instance with given set of prefixes and
    /// card lengths
    ///
    /// - Parameters:
    ///   - prefixes: Array of prefixes describing given card type
    ///   - lengths: Array of lengths describing given card type
    CCValidator(CreditCardPrefix[] prefixes, int[] lengths) {
        this.prefixes = prefixes;
        this.lengths = lengths;
    }


    CCValidator(CreditCardType type) {

        CreditCardSpec specs = new CreditCardSpec(new CreditCardPrefix[0], new int[0]);

        switch (type) {
            case AmericanExpress:
                specs = americanExpressSpecs();
                break;
            case Dankort:
                specs = dankortSpecs();
                break;
            case DinersClub:
                specs = dinersClubSpecs();
                break;
            case Discover:
                specs = discoverSpecs();
                break;
            case JCB:
                specs = jcbSpecs();
                break;
            case Maestro:
                specs = maestroSpecs();
                break;
            case MasterCard:
                specs = masterCardSpecs();
                break;
            case UnionPay:
                specs = unionPaySpecs();
                break;
            case VisaElectron:
                specs = visaElectronSpecs();
                break;
            case Visa:
                specs = visaSpecs();
                break;
            default:
        }

        this.prefixes = specs.prefixes;
        this.lengths = specs.lengths;

    }


    private CreditCardSpec americanExpressSpecs() {

        CreditCardPrefix[] prefixes = new CreditCardPrefix[2];
        prefixes[0] = new CreditCardPrefix(34, 34, 2);
        prefixes[1] = new CreditCardPrefix(37, 37, 2);

        int[] lengths = new int[1];
        lengths[0] = 15;

        return new CreditCardSpec(prefixes, lengths);
    }


    private CreditCardSpec dankortSpecs() {

        CreditCardPrefix[] prefixes = new CreditCardPrefix[1];
        prefixes[0] = new CreditCardPrefix(5019, 5019, 4);

        int[] lengths = new int[1];
        lengths[0] = 16;

        return new CreditCardSpec(prefixes, lengths);
    }


    private CreditCardSpec dinersClubSpecs() {

        CreditCardPrefix[] prefixes = new CreditCardPrefix[4];
        prefixes[0] = new CreditCardPrefix(300, 305, 3);
        prefixes[1] = new CreditCardPrefix(309, 309, 3);
        prefixes[2] = new CreditCardPrefix(36, 36, 3);
        prefixes[3] = new CreditCardPrefix(38, 38, 2);

        int[] lengths = new int[1];
        lengths[0] = 14;

        return new CreditCardSpec(prefixes, lengths);
    }


    private CreditCardSpec discoverSpecs() {

        CreditCardPrefix[] prefixes = new CreditCardPrefix[4];
        prefixes[0] = new CreditCardPrefix(6011, 6011, 4);
        prefixes[1] = new CreditCardPrefix(622126, 622925, 6);
        prefixes[2] = new CreditCardPrefix(644, 649, 3);
        prefixes[3] = new CreditCardPrefix(65, 65, 2);

        int[] lengths = new int[2];
        lengths[0] = 16;
        lengths[1] = 19;

        return new CreditCardSpec(prefixes, lengths);
    }


    private CreditCardSpec jcbSpecs() {

        CreditCardPrefix[] prefixes = new CreditCardPrefix[1];
        prefixes[0] = new CreditCardPrefix(3528, 3589, 4);

        int[] lengths = new int[1];
        lengths[0] = 16;

        return new CreditCardSpec(prefixes, lengths);
    }


    private CreditCardSpec maestroSpecs() {

        CreditCardPrefix[] prefixes = new CreditCardPrefix[4];
        prefixes[0] = new CreditCardPrefix(50, 50, 2);
        prefixes[1] = new CreditCardPrefix(56, 58, 2);
        prefixes[2] = new CreditCardPrefix(60, 61, 2);
        prefixes[3] = new CreditCardPrefix(63, 69, 2);

        int[] lengths = new int[8];
        lengths[0] = 12;
        lengths[1] = 13;
        lengths[2] = 14;
        lengths[3] = 15;
        lengths[4] = 16;
        lengths[5] = 17;
        lengths[6] = 18;
        lengths[7] = 19;

        return new CreditCardSpec(prefixes, lengths);
    }



    private CreditCardSpec masterCardSpecs() {

        CreditCardPrefix[] prefixes = new CreditCardPrefix[2];
        prefixes[0] = new CreditCardPrefix(2221, 2720, 4);
        prefixes[1] = new CreditCardPrefix(51, 56, 2);


        int[] lengths = new int[1];
        lengths[0] = 16;

        return new CreditCardSpec(prefixes, lengths);
    }


    private CreditCardSpec unionPaySpecs() {

        CreditCardPrefix[] prefixes = new CreditCardPrefix[2];
        prefixes[0] = new CreditCardPrefix(62, 62, 2);
        prefixes[1] = new CreditCardPrefix(88, 88, 2);

        int[] lengths = new int[4];
        lengths[0] = 16;
        lengths[1] = 17;
        lengths[2] = 18;
        lengths[3] = 19;

        return new CreditCardSpec(prefixes, lengths);
    }



    private CreditCardSpec visaElectronSpecs() {

        CreditCardPrefix[] prefixes = new CreditCardPrefix[7];
        prefixes[0] = new CreditCardPrefix(4026, 4026, 4);
        prefixes[1] = new CreditCardPrefix(417500, 417500, 6);
        prefixes[2] = new CreditCardPrefix(4405, 4405, 2);
        prefixes[3] = new CreditCardPrefix(4508, 4508, 2);
        prefixes[4] = new CreditCardPrefix(4844, 4844, 2);
        prefixes[5] = new CreditCardPrefix(4913, 4913, 2);
        prefixes[6] = new CreditCardPrefix(4917, 4917, 2);

        int[] lengths = new int[1];
        lengths[0] = 16;

        return new CreditCardSpec(prefixes, lengths);
    }



    private CreditCardSpec visaSpecs() {

        CreditCardPrefix[] prefixes = new CreditCardPrefix[1];
        prefixes[0] = new CreditCardPrefix(4, 4, 1);

        int[] lengths = new int[1];
        lengths[0] = 16;

        return new CreditCardSpec(prefixes, lengths);
    }


    static private CreditCardType[] allTypes() {
        CreditCardType[] types = new CreditCardType[10];

        types[0] = CreditCardType.AmericanExpress;
        types[1] = CreditCardType.Dankort;
        types[2] = CreditCardType.DinersClub;
        types[3] = CreditCardType.Discover;
        types[4] = CreditCardType.JCB;
        types[5] = CreditCardType.Maestro;
        types[6] = CreditCardType.MasterCard;
        types[7] = CreditCardType.UnionPay;
        types[8] = CreditCardType.VisaElectron;
        types[9] = CreditCardType.Visa;

        return types;
    }



    private boolean validatePrefixSpecs(String number) {

        for (CreditCardPrefix prefix : this.prefixes ) {
            if (number.length() >= prefix.prefixLength) {
                String numberPrefix = number.substring(0, prefix.prefixLength);

                try {
                    int n = Integer.parseInt(numberPrefix);
                    if (n >= prefix.rangeStart && n <= prefix.rangeEnd) {
                        return true;
                    }
                }
                catch (NumberFormatException nfe) {
                    // just eat this and let the function run out
                }
            }
        }

        return  false;
    }


    private boolean validateLengthSpecs(String number) {

        for(int len : this.lengths) {
            if (number.length() == len) {
                return true;
            }
        }

        return false;
    }


    static private ArrayList<CreditCardType> possibleTypesCheckingLengthOnly(String number) {

        CreditCardType[] allTypes = CCValidator.allTypes();
        ArrayList<CreditCardType> possibleTypes = new ArrayList<CreditCardType>();

        for (CreditCardType type : allTypes) {
            CCValidator validator = new CCValidator(type);

            if (validator.validateLengthSpecs(number)) {
                possibleTypes.add(type);
            }
        }

        return possibleTypes;
    }


    static private boolean validateWithLuhnAlgorithm(String number) {

        try {
            long n = Long.parseLong(number);
        }
        catch (NumberFormatException nfe) {
            // if the string can't convert to a long it is not a cc num
            return false;
        }

        int nDigits = number.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--)
        {

            int d = number.charAt(i) - '0';

            if (isSecond == true)
                d = d * 2;

            // We add two digits to handle cases that make two digits after doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }

        return (nSum % 10 == 0);
    }


    static private CreditCardType typeCheckingPrefixOnly(String number, CreditCardType[] types) {

        String cleanedNumber = number.replaceAll("[\\n\\t ]", "");

        for (CreditCardType t : types) {
            CCValidator validator = new CCValidator(t);
            if (validator.validatePrefixSpecs(cleanedNumber)) {
                return t;
            }
        }

        return CreditCardType.NotRecognized;
    }



    static private CreditCardType typeCheckingPrefixOnly(String number) {
        return CCValidator.typeCheckingPrefixOnly(number, CCValidator.allTypes());
    }



    static boolean validate(String number, boolean validatePrefix, boolean validateLength, boolean useLuhn) {

        String cleanedNumber = number.replaceAll("[\\n\\t ]", "");

        ArrayList<CreditCardType> possibleTypesByLength = CCValidator.possibleTypesCheckingLengthOnly(cleanedNumber);
        if (validateLength && possibleTypesByLength.isEmpty()) {
            return false;
        }

        CreditCardType typeByPrefix = CCValidator.typeCheckingPrefixOnly(cleanedNumber);
        if (validatePrefix && typeByPrefix == CreditCardType.NotRecognized) {
            return false;
        }

        boolean isLengthTypeAndPrefixTypeSame = possibleTypesByLength.contains(typeByPrefix);
        if (validateLength && validatePrefix && (isLengthTypeAndPrefixTypeSame == false)) {
            return false;
        }

        if (useLuhn && CCValidator.validateWithLuhnAlgorithm(cleanedNumber) == false) {
            return false;
        }

        return true;
    }


    static boolean validate(String number) {
        return CCValidator.validate(number, true, true, true);
    }


    static boolean validateDate(String dateText) {

        return  dateText.matches("([0-9][1-9])\\/([0-9][1-9])$");
    }
} // end class
