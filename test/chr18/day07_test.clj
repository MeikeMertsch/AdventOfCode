(ns christmas.day07-test
  (:require [christmas.day07 :as chr]
  		  	[expectations :refer :all]
            [christmas.core :refer :all]
            [clojure.string :as str]))
(comment
(defn getFile [fileName]
	(->> (slurp fileName)
		 (clojure.string/split-lines)
		 (map #(str/replace % (re-pattern "Step ") ""))
		 (map #(str/replace % (re-pattern " can begin.") ""))
		 (map #(str/split % (re-pattern " must be finished before step ")))))

(def allofthem ["A" "B" "C" "D" "E" "F"])
(def realAllofthem ["A" "B" "C" "D" "E" "F" "G" "H" "I" "J" "K" "L" "M" "N" "O" "P" "Q" "R" "S" "T" "U" "V" "W" "X" "Y" "Z"])
(def realFile (getFile "resources/day06-10/Input07real"))
(def file (getFile "resources/day06-10/Input07"))

(expect ["A" "B" "D" "E" "H" "I" "J" "K" "L" "N" "O" "P" "Q" "R" "S" "T" "U" "V" "W" "X" "Y" "Z"]
 (sort (vec (into #{} (map last realFile)))))

(expect "C" (chr/find-next ["A" "B" "C" "D" "E" "F"] file))

(expect "CABDFE" (chr/exercise07 allofthem file))
(expect "CFGHAEMNBPRDISVWQUZJYTKLOX" (chr/exercise07 realAllofthem realFile))

;---------------------

(expect 15 (chr/exercise07b file 2 allofthem 0))
(expect 828 (chr/exercise07b realFile 5 realAllofthem 60))

(expect 5 (chr/duration "E" 0))
(expect 26 (chr/duration "Z" 0))
(expect 86 (chr/duration "Z" 60))

(expect {"A" 0 "Z" 25} (chr/tick {"A" 1 "Z" 26}))
(expect {"C" 2 "N" 13} (chr/tick {"C" 3 "N" 14}))

(expect {"Z" 25} (chr/free-up-workers {"A" 0 "Z" 25} ["A"]))
(expect {} (chr/free-up-workers {"A" 0 "Z" 0} ["A" "Z"])) 

(expect ["A"] (chr/finish-work {"A" 1 "Z" 25}))
(expect ["A" "B"] (chr/finish-work {"A" 1 "C" 2 "B" 1 "Z" 25}))

(expect [["A" 1] ["N" 14] ["K" 11]] (chr/time-candidates ["A" "N" "K"] 0))

(expect {"B" 2 "Z" 25 "A" 1 "N" 14 "K" 11} (chr/get-to-work 5 {"B" 2 "Z" 25} [["A" 1] ["N" 14] ["K" 11]]))
(expect {"B" 2 "Z" 25 "A" 1 } (chr/get-to-work 3 {"B" 2 "Z" 25} [["A" 1] ["N" 14] ["K" 11]]))
(expect {"A" 2 "G" 25 "N" 14} (chr/get-to-work 5 {"A" 2 "G" 25} [["N" 14]]))
(expect {"A" 2 "G" 25} (chr/get-to-work 5 {"A" 2 "G" 25} []))
(expect {"B" 2 "Z" 25 "A" 1 "N" 14 "K" 11} (chr/get-to-work 5 {"B" 2 "Z" 25 "A" 1 "N" 14 "K" 11}  [["Q" 1] ["R" 14] ["S" 11]]))

(expect [["C" 3]] (chr/tcandidates allofthem file 0))

(expect (drop 2 file) (chr/loosen-restrictions file ["C"]))

(expect  #{"A" "B" "E" "F"} (chr/throw-out-finished (into #{} allofthem) ["C" "D"] {}))



)
