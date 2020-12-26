(ns christmas.chr20.day22
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]
            [clojure.pprint :as pp]))

(defn parse [input]
  (->> (str/split (slurp input) #"\n\n")
       (map str/split-lines)
       (map rest)
       (mapv (partial mapv t/parse-int))))

(defn comparison [game]
  (->> (apply map vector game)
       (first)))

(defn winner [[a b]]
  (if (< a b) 1 0))

(defn play [game]
  (->> (comparison game)
       (#(update game (winner %) conj (apply max %) (apply min %)))
       (t/map-kv rest)
       (mapv vec)))

(defn day22 [input]
  (->> (parse input)
       (iterate play)
       (t/find-first #(some empty? %))
       (t/find-first not-empty)
       (reverse)
       (map * (rest (range)))
       (apply +)))

(defn parse-b [input]
  (->> (str/split (slurp input) #"\n\n")
       (map str/split-lines)
       (map rest)
       (map reverse)
       (mapv (partial mapv t/parse-int))))


(defn to-game [input]
  (->> (hash-map :p1-hands [#{}]
                 :p2-hands [#{}]
                 :games (vector input))))

(defn play-hand-cards [[hand-1 hand-2] winner]
  (let [t1 (peek hand-1)
        t2 (peek hand-2)
        r1 (pop hand-1)
        r2 (pop hand-2)]
    (if (= 0 winner) ; player 1 wins
      (vector (into [t2 t1] r1) (vec r2))
      (vector (vec r1) (into [t1 t2] r2)))))


(defn win-game [{:keys [games] :as game} winner]
  ;(doall (println games winner game))
  (-> (update game :games pop)
      (update :p1-hands pop)
      (update :p2-hands pop)
      (update-in [:games (- (count games) 2)] play-hand-cards winner)))

(defn prevent-loop [{:keys [p1-hands p2-hands games] :as game}]
  (if (or ((last p1-hands) (first (peek games)))
          ((last p2-hands) (last (peek games))))
    (win-game game 0)
    game))


(defn cannot-recur? [hand]
  ;(doall (println top rest))
  (<= (peek hand) (count (pop hand))))

(defn play-hands [hands]
  (let [compare (map peek hands)
        popped-hands (map pop hands)]
    (if (apply < compare)
      compare
      popped-hands)))

(defn play-hands-2 [[hand-1 hand-2]]
  (let [t1 (peek hand-1)
        t2 (peek hand-2)]
    (play-hand-cards [hand-1 hand-2] (if (< t2 t1) 0 1))))

(defn play-b [{:keys [games p1-hands p2-hands] :as game}]
  (let [hands (peek games)]
    (-> (update-in game [:p1-hands (dec (count p1-hands))] conj (first hands))
        (update-in [:p2-hands (dec (count p2-hands))] conj (last hands))
        (update-in [:games (dec (count games))] play-hands-2))))

(defn init-sub-game [hands]
  (mapv #(subvec (pop %) (- (count %) (inc (peek %)))) hands))


(defn play-or-recur [{:keys [games] :as game}]
  ;(doall (println games (first games)))
  (let [hands (peek games)]
    (->> (if (every? cannot-recur? hands)
           (-> (update game :games conj (init-sub-game hands))
               (update :p1-hands conj #{})
               (update :p2-hands conj #{}))
           (play-b game)))))

(defn ended? [hands]
  (some empty? hands))

(defn game-won? [{:keys [games] :as game}]
  (if (ended? (peek games))
    (win-game game (if (empty? (peek (peek games))) 0 1))
    game))

(defn play-recursive [game]
  (->> (game-won? game)
       (prevent-loop)
       (play-or-recur)))

(defn day22b [input]
  (->> (parse-b input)
       (to-game)
       (iterate play-recursive)
       (t/find-first #(ended? (first (:games %))))
       (:games)
       (flatten)
       (map * (rest (range)))
       (apply +)))
