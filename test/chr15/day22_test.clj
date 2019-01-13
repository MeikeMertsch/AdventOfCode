(ns christmas.chr15.day22-test
  (:require [christmas.chr15.day22 :as chr]
  		  	[christmas.tools :as t]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(defn build-map [[hp damage]]
	{:player {:mana {:amount 500 :steps []} :hp 50} :boss {:hp hp :damage damage}})

(defn parse-file [file]
	(->> (slurp file)
		 (re-seq #"\d+")
		 (map t/parse-int)
		 build-map))

(def file (parse-file "resources/christmas/chr15/day22real"))

(def game-0 file)
(def t-game-0 {:player {:mana {:amount 250 :steps []} :hp 10} :boss {:hp 13 :damage 8}})
(def t-game-1 {:player {:mana {:amount 100 :steps []} :hp 50} :boss {:hp 71 :damage 10}})
(def game-missile {:player {:mana {:amount 447 :steps [53]}, :hp 50}, :boss {:hp 67, :damage 10}})
(def game-drain {:player {:mana {:amount 427 :steps [73]}, :hp 52}, :boss {:hp 69, :damage 10}})
(def game-poison {:player {:mana {:amount 327 :steps [173]}, :hp 50}, :boss {:hp 71, :damage 10} :effects {:poison 6}})
(def game-shield {:player {:mana {:amount 387 :steps [113]}, :hp 50}, :boss {:hp 71, :damage 10} :effects {:shield 6}})
(def game-recharge {:player {:mana {:amount 271 :steps [229]}, :hp 50}, :boss {:hp 71, :damage 10} :effects {:recharge 5}})

(def game-0-after-boss {:player {:mana {:amount 500 :steps []} :hp 40} :boss {:hp 71 :damage 10}})
(def game-shield-after-boss {:player {:mana {:amount 387 :steps [113]}, :hp 47}, :boss {:hp 71, :damage 10} :effects {:shield 6}})

(def game-poison-2 {:player {:mana {:amount 327 :steps [173]}, :hp 50}, :boss {:hp 68, :damage 10} :effects {:poison 5}})
(def game-shield-2 {:player {:mana {:amount 387 :steps [113]}, :hp 50}, :boss {:hp 71, :damage 10} :effects {:shield 5}})
(def game-recharge-2 {:player {:mana {:amount 372 :steps [229]}, :hp 50}, :boss {:hp 71, :damage 10} :effects {:recharge 4}})

(expect game-missile (chr/magic-missile game-0))
(expect game-drain (chr/drain game-0))
(expect game-shield (chr/shield game-0))
(expect game-poison (chr/poison game-0))
(expect game-recharge (chr/recharge game-0))

(expect game-poison-2 (chr/apply-effects game-poison))
(expect game-shield-2 (chr/apply-effects game-shield))
(expect game-recharge-2 (chr/apply-effects game-recharge))

(expect game-0-after-boss (chr/boss-move game-0))
(expect game-shield-after-boss (chr/boss-move game-shield))

(expect [:shield] (chr/running-effects game-shield))
(expect [:shield :poison :recharge] (chr/too-costly-actions t-game-1))

;(expect 1824 (chr/day22 game-0)) ; 50 s
(expect 1937 (chr/day22b game-0)) ; 2.5 s
)









