(ns christmas.chr15.day21-test
  (:require [christmas.chr15.day21 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/christmas/chr15/day21real"))

(def initial-state {:hp 100})
(def weak-player {:hp 100, :cost 8, :damage 4, :armor 0})
(def test-player {:hp 8, :cost 8, :damage 5, :armor 5})
(def test-boss {:hp 12, :damage 7, :armor 2})

(def boss (chr/parse-boss file))

;(expect 1290 (count (chr/combos)))

(expect weak-player (first (map (partial chr/equip initial-state) (chr/combos))))

(expect true (chr/wins? test-player test-boss))

(expect 4 (chr/rounds-to-win test-player test-boss))
(expect 4 (chr/rounds-to-win test-boss test-player))

(expect 91 (chr/day21 initial-state boss))
(expect 158 (chr/day21b initial-state boss))
)