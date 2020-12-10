(ns christmas.chr20.day04
  (:require [clojure.string :as str]
            [christmas.tools :as t]))


(defn parsefile [filename]
  (-> (slurp filename)
      (str/split #"\n\n")))

(defn mapify-keys [string]
  (re-seq #"\w+:" string))

(defn day04 [input]
  (->> (parsefile input)
       (map mapify-keys)
       (map #(every? (set %) ["ecl:" "pid:" "eyr:" "hcl:" "byr:" "iyr:" "hgt:"]))
       (filter true?)
       count))

(defn mapify [string]
  (->> (re-seq #"\w+:[#|\w|\d]+" string)
       (map #(str/split % #":"))
       flatten
       (apply hash-map)))

(defn validate-height [string]
  (and (->> (re-matches #"\d+(in|cm)" string)
            first)
       (->> (re-seq #"\d+|in|cm" string)
            (#(if (= "cm" (second %))
                (<= 150 (t/parse-int (first %)) 193)
                (<= 59 (t/parse-int (first %)) 76))))))

(defn validate [mappings]
  (if
   (and
    (->> (mappings "byr") (#(and (some? %) (re-matches #"\d{4}" %) (<= 1920 (t/parse-int %) 2002))))
    (->> (mappings "iyr") (#(and (some? %) (re-matches #"\d{4}" %) (<= 2010 (t/parse-int %) 2020))))
    (->> (mappings "eyr") (#(and (some? %) (re-matches #"\d{4}" %) (<= 2020 (t/parse-int %) 2030))))
    (->> (mappings "hcl") (#(and (some? %) (re-matches #"#(\d|[a-f]){6}" %))))
    (->> (mappings "ecl") (#(and (some? %) (re-matches #"amb|blu|brn|gry|grn|hzl|oth" %))))
    (->> (mappings "pid") (#(and (some? %) (re-matches #"\d{9}" %))))
    (->> (mappings "hgt") (#(validate-height %)))) true false))

(defn day04b [input]
  (->> (parsefile input)
       (map mapify)
       (filter #(every? (set (keys %)) ["ecl" "pid" "eyr" "hcl" "byr" "iyr" "hgt"]))
       (map validate)
       (filter true?)
       count
       ))


