(ns christmas.chr15.day19-test
  (:require [christmas.chr15.day19 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def input-instr (slurp "resources/christmas/chr15/day19real_1"))
(def input-string (slurp "resources/christmas/chr15/day19real_2"))

(expect 506 (count (first (chr/change-molecule (first (chr/parse-instructions input-instr)) input-string))))
(expect (- 66 14) (count (chr/change-molecule ["Ca" "C"] input-string)))
(expect 535 (chr/day19 input-instr input-string))

(def t-instr (apply str (interpose "\n" (reverse (str/split-lines "e => H
e => O
H => HO
H => OH
O => HH")))))

(def t-input "HOH")
(def t-input-2 "HOHOHO")

(expect 6 (chr/day19b t-instr t-input-2))
(expect 212 (chr/day19b input-instr input-string))
)