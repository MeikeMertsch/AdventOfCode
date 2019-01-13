(ns christmas.chr18.day24-test
 (:require [christmas.chr18.day24 :as chr]
  		   [expectations :refer :all]
  		   [clojure.string :as str]))

(comment 
(def i2-text "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3")

(def i1 {:units 17 
		 :hp 5390 
		 :weak #{:radiation :bludgeoning} 
		 :immunity #{} 
		 :damage 4507 
		 :init 2 
		 :d-type :fire
		 :target nil})

(def i2 {:units 989 
		 :hp 1274 
		 :immunity #{:fire} 
		 :weak #{:bludgeoning :slashing} 
		 :damage 25 
		 :init 3 
		 :d-type :slashing
		 :target nil})

(def v1 {:units 801 
		 :hp 4706 
		 :weak #{:radiation} 
		 :immunity #{} 
		 :damage 116 
		 :init 1 
		 :d-type :bludgeoning
		 :target nil})

(def v2 {:units 4485 
		 :hp 2961 
		 :immunity #{:radiation} 
		 :weak #{:fire :cold} 
		 :damage 12 
		 :init 4 
		 :d-type :slashing
		 :target nil})

(defn get-file [file]
	(->> (slurp file)
		 str/split-lines))

(def file-immune (get-file "resources/chr18/Input24_immune"))
(def real-file-immune (get-file "resources/chr18/Input24real_immune"))
(def file-viruses (get-file "resources/chr18/Input24_viruses"))
(def real-file-viruses (get-file "resources/chr18/Input24real_viruses"))

;(expect "" (map chr/parse-group real-file-viruses))

(expect 76619 (chr/effective-power i1))
(expect 24725 (chr/effective-power i2))
(expect 92916 (chr/effective-power v1))
(expect 53820 (chr/effective-power v2))

(expect [2 3] (map :init (chr/choosing-order [i1 i2])))
(expect [1 2 4 3] (map :init (chr/choosing-order [i2 v2 v1 i1])))

(expect false (chr/has-weakness :radiation i2))
(expect true (chr/has-weakness :bludgeoning i2))

(expect v2 (chr/choose-target i1 [i2 v1 v2]))
(expect v1 (chr/choose-target v2 [i1 v1]))

;(expect "" (chr/apply-target [[] [v1 v2]] i1))
;(expect "" (chr/apply-target [[] [v1 v2]] i2))
;(expect "" (chr/apply-target [[] [i1 i2]] v1))
;(expect "" (chr/apply-target [[] [i1 i2]] v2))

(def system {:immune #{i1 i2} :viruses #{v1 v2}})

(expect #{[1 2] [4 3]} (into #{} (map #(vector (:init %) (:target %)) (chr/choose-targets #{v1 v2} #{i1 i2}))))
(expect #{[2 4] [3 1]} (into #{} (map #(vector (:init %) (:target %)) (chr/choose-targets #{i1 i2} #{v1 v2}))))

;(expect "" (chr/selection-phase {:immune [i1 i2] :viruses [v1 v2]}))

(expect [[4 :viruses][3 :immune][2 :immune][1 :viruses]] (chr/attack-order system))

(expect v2 (chr/find-attacker [4 :viruses] system))
(expect nil (chr/find-attacker [5 :viruses] system))

(expect i2 (chr/find-target [4 :viruses] (update-in v2 [:target] (constantly 3)) system))
(expect nil (chr/find-target [5 :viruses] nil system))

(expect i2 (chr/parse-group i2-text))

(expect system (chr/system file-immune file-viruses))

;(expect "" (chr/attacking-phase (chr/selection-phase system)))
;(expect "" (chr/attack (chr/selection-phase system) [4 :viruses]))

(expect 5216 (chr/day24 file-immune file-viruses))
(expect 13331 (chr/day24 real-file-immune real-file-viruses))
;(expect-focused 5216 (chr/day24b real-file-immune real-file-viruses))
;(expect-focused 0 (+ 1386 733 238 885 1304 67 789 1047 1027))
)